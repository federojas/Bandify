package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Genre;
import ar.edu.itba.paw.model.Location;
import ar.edu.itba.paw.model.Role;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exceptions.AuditionNotFoundException;
import ar.edu.itba.paw.model.exceptions.GenreNotFoundException;
import ar.edu.itba.paw.model.exceptions.LocationNotFoundException;
import ar.edu.itba.paw.persistence.Audition;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.form.ApplicationForm;
import ar.edu.itba.paw.webapp.form.AuditionForm;
import ar.edu.itba.paw.webapp.security.services.SecurityFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@Controller
public class AuditionsController {

    private final AuditionService auditionService;
    private final RoleService roleService;
    private final GenreService genreService;
    private final LocationService locationService;
    private final MailingService mailingService;
    private final SecurityFacade securityFacade;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private Environment environment;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditionsController.class);

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
        return auditions(1);
    }

    @RequestMapping(value = "/auditions", method = {RequestMethod.GET})
    public ModelAndView auditions( @RequestParam(value = "page", defaultValue = "1") int page) {
        final ModelAndView mav = new ModelAndView("views/auditions");
        // TODO: Error controller
        int lastPage = auditionService.getTotalPages(null);
        if(page < 0 || (page > lastPage && lastPage != 0))
            return new ModelAndView("errors/404");
        List<Audition> auditionList = auditionService.getAll(page);
        mav.addObject("auditionList", auditionList);
        mav.addObject("currentPage", page);
        mav.addObject("lastPage", lastPage);
        mav.addObject("user", securityFacade.getCurrentUser());
        return mav;
    }

    @RequestMapping(value = "/search", method = {RequestMethod.GET})
    public ModelAndView search( @RequestParam(value = "page", defaultValue = "1") int page,
                                @RequestParam(value = "query", defaultValue = "") String query ) {
        final ModelAndView mav = new ModelAndView("views/search");
        int lastPage = auditionService.getTotalPages(query);
        if(page < 0 || (page > lastPage && lastPage != 0))
            return new ModelAndView("errors/404");
        if(query.equals(""))
            return auditions(1);
        List<Audition> auditionList = auditionService.search(page, query);
        mav.addObject("auditionList", auditionList);
        mav.addObject("currentPage", page);
        mav.addObject("query", query);
        mav.addObject("lastPage", lastPage);
        return mav;
    }

    @RequestMapping(value = "/auditions/{id}", method = {RequestMethod.GET})
    public ModelAndView audition(@ModelAttribute("applicationForm") final ApplicationForm applicationForm,
                                 @PathVariable long id) {
        // TODO : es necesario este if? sino con el else de abajo seria suficiente creo
        // TODO : consultar por qué el error controller no agarra la excepción
        if(id < 0 || id > auditionService.getMaxAuditionId())
            throw new AuditionNotFoundException();
        final ModelAndView mav = new ModelAndView("views/audition");
        Optional<Audition> audition = auditionService.getAuditionById(id);
        if (audition.isPresent()) {
            mav.addObject("audition", audition.get());
            mav.addObject("user",securityFacade.getCurrentUser());
        } else {
            throw new AuditionNotFoundException();
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
        // TODO: FOTO NO FUNCIONA EN AUDITION-APPLICATION.HTML
       try {
           Optional<Audition> aud = auditionService.getAuditionById(id);
           if (aud.isPresent()) {
               Locale locale = LocaleContextHolder.getLocale();
               final String url = new URL("http", environment.getRequiredProperty("app.base.url"), "/paw-2022a-03/").toString();
               Map<String, Object> mailData = new HashMap<>();
               mailData.put("content", applicationForm.getMessage());
               mailData.put("goToBandifyURL", url);

               mailingService.sendEmail(securityFacade.getCurrentUser(), aud.get().getEmail(),
                       messageSource.getMessage("audition-application.subject",null,locale),
                       "audition-application", mailData, locale);
           }
        } catch (MessagingException e) {
           LOGGER.warn("Audition application email threw messaging exception");
        } catch (MalformedURLException e) {
           LOGGER.warn("Audition application email threw url exception");
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

    @RequestMapping(value="/newAudition", method = {RequestMethod.POST})
    public ModelAndView postNewAudition(@Valid @ModelAttribute("auditionForm") final AuditionForm auditionForm,
                                        final BindingResult errors) {

        if(errors.hasErrors()) {
            return newAudition(auditionForm);
        }

        auditionService.create(auditionForm.toBuilder(1, securityFacade.getCurrentUser().getEmail()).
                location(locationService.getLocationByName(auditionForm.getLocation()).orElseThrow(LocationNotFoundException::new)).
                lookingFor(roleService.validateAndReturnRoles(auditionForm.getLookingFor())).
                musicGenres(genreService.validateAndReturnGenres(auditionForm.getMusicGenres()))
        );

        return auditions(1);
    }

    @RequestMapping(value = "/success", method = {RequestMethod.GET})
    public ModelAndView success() {
        return new ModelAndView("views/successMsg");
    }

}
