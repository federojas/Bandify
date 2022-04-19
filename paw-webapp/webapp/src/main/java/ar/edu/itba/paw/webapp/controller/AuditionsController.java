package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Genre;
import ar.edu.itba.paw.model.Location;
import ar.edu.itba.paw.model.Role;
import ar.edu.itba.paw.model.exceptions.GenreNotFoundException;
import ar.edu.itba.paw.model.exceptions.LocationNotFoundException;
import ar.edu.itba.paw.model.exceptions.RoleNotFoundException;
import ar.edu.itba.paw.persistence.Audition;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.form.ApplicationForm;
import ar.edu.itba.paw.webapp.form.AuditionForm;
import ar.edu.itba.paw.webapp.security.services.SecurityFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class AuditionsController {

    private final AuditionService auditionService;
    private final RoleService roleService;
    private final GenreService genreService;
    private final LocationService locationService;
    private final MailingService mailingService;
    private final SecurityFacade securityFacade;

    @Autowired
    public AuditionsController(final AuditionService auditionService, final MailingService mailingService,
                               final GenreService genreService, final LocationService locationService,
                               final RoleService roleService, SecurityFacade securityFacade) {
        this.auditionService = auditionService;
        this.roleService = roleService;
        this.genreService = genreService;
        this.locationService = locationService;
        this.mailingService = mailingService;
        this.securityFacade = securityFacade;
    }

    @RequestMapping(value = "/", method = {RequestMethod.GET})
    public ModelAndView home() {
        return auditions();
    }

    @RequestMapping(value = "/auditions", method = {RequestMethod.GET})
    public ModelAndView auditions() {
        final ModelAndView mav = new ModelAndView("views/auditions");
        List<Audition> auditionList = auditionService.getAll(1);
        mav.addObject("auditionList", auditionList);
        return mav;
    }

    @RequestMapping(value = "/auditions/{id}", method = {RequestMethod.GET})
    public ModelAndView audition(@ModelAttribute("applicationForm") final ApplicationForm applicationForm,
                                 @PathVariable long id) {
        final ModelAndView mav = new ModelAndView("views/audition");

        Optional<Audition> audition = auditionService.getAuditionById(id);
        if (audition.isPresent()) {
            mav.addObject("audition", audition.get());
        } else {
            return badFormData();
        }
        return mav;
    }

    @RequestMapping(value = "/apply", method = {RequestMethod.POST})
    public ModelAndView apply(@Valid @ModelAttribute("applicationForm") final ApplicationForm applicationForm,
                              final BindingResult errors,
                              @RequestParam(required = true) final long id ){
        if (errors.hasErrors()) {
            return audition(applicationForm,id);
        }

        // TODO: el email deberia estar dentro de auditionService
       try {
           Optional<Audition> aud = auditionService.getAuditionById(id);
           if (aud.isPresent()) {
               mailingService.sendAuditionEmail(aud.get().getEmail(), securityFacade.getCurrentUser(), applicationForm.getMessage(), LocaleContextHolder.getLocale());
           }
        } catch (MessagingException e) {
           //TODO: IMPRESION EN LOG
            e.printStackTrace();
        }
        return success();
    }

    @RequestMapping(value = "/newAudition", method = {RequestMethod.GET})
    public ModelAndView newAudition(@ModelAttribute("auditionForm") final AuditionForm auditionForm) {
        final ModelAndView mav = new ModelAndView("views/auditionForm");

        List<Role> roleList = roleService.getAll();
        List<Genre> genreList = genreService.getAll();
        List<Location> locationList = locationService.getAll();

        mav.addObject("roleList", roleList);
        mav.addObject("genreList", genreList);
        mav.addObject("locationList", locationList);

        return mav;
    }

    @RequestMapping(value="/postAudition", method = {RequestMethod.POST})
    public ModelAndView postNewAudition(@Valid @ModelAttribute("auditionForm") final AuditionForm auditionForm,
                                        final BindingResult errors) {

        if(errors.hasErrors()) {
            return newAudition(auditionForm);
        }

        auditionService.create(auditionForm.toBuilder(1, securityFacade.getCurrentUser().getEmail()).
                location(locationService.getLocation(auditionForm.getLocation()).orElseThrow(LocationNotFoundException::new)).
                lookingFor(roleService.validateAndReturnRoles(auditionForm.getLookingFor())).
                musicGenres(genreService.validateAndReturnGenres(auditionForm.getMusicGenres()))
        );

        return auditions();
    }

    @ExceptionHandler({LocationNotFoundException.class, GenreNotFoundException.class, RoleNotFoundException.class})
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ModelAndView badFormData() {
        return new ModelAndView("errors/404");
    }

    @RequestMapping(value = "/success", method = {RequestMethod.GET})
    public ModelAndView success() {
        return new ModelAndView("views/successMsg");
    }


}
