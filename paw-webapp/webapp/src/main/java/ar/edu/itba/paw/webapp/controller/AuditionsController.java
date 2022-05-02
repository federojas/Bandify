package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.persistence.*;
import ar.edu.itba.paw.model.exceptions.AuditionNotFoundException;
import ar.edu.itba.paw.model.exceptions.LocationNotFoundException;
import ar.edu.itba.paw.model.exceptions.UserNotFoundException;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.form.ApplicationForm;
import ar.edu.itba.paw.webapp.form.AuditionForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.*;

@Controller
public class AuditionsController {

    private final AuditionService auditionService;
    private final RoleService roleService;
    private final GenreService genreService;
    private final LocationService locationService;
    private final UserService userService;
    private final ApplicationService applicationService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditionsController.class);

    @Autowired
    public AuditionsController(final AuditionService auditionService,
                               final GenreService genreService, final LocationService locationService,
                               final RoleService roleService, final UserService userService,
                               final ApplicationService applicationService) {
        this.auditionService = auditionService;
        this.roleService = roleService;
        this.genreService = genreService;
        this.locationService = locationService;
        this.userService = userService;
        this.applicationService = applicationService;
    }

    @RequestMapping(value = "/", method = {RequestMethod.GET})
    public ModelAndView home() {
        if (! (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            return new ModelAndView("redirect:/auditions");
        }
        return new ModelAndView("redirect:/welcome");
    }

    @RequestMapping(value = "/auditions", method = {RequestMethod.GET})
    public ModelAndView auditions( @RequestParam(value = "page", defaultValue = "1") int page) {
        final ModelAndView mav = new ModelAndView("views/auditions");
        // TODO: Error controller
        int lastPage = auditionService.getTotalPages(null);
        if(lastPage == 0)
            lastPage = 1;
        if(page < 0 || page > lastPage)
            return new ModelAndView("errors/404");
        List<Audition> auditionList = auditionService.getAll(page);
        mav.addObject("auditionList", auditionList);
        mav.addObject("currentPage", page);
        mav.addObject("lastPage", lastPage);

        return mav;
    }

    @RequestMapping(value = "/search", method = {RequestMethod.GET})
    public ModelAndView search( @RequestParam(value = "page", defaultValue = "1") int page,
                                @RequestParam(value = "query", defaultValue = "") String query ) {
        final ModelAndView mav = new ModelAndView("views/search");
        int lastPage = auditionService.getTotalPages(query);
        if(lastPage == 0)
            lastPage = 1;
        if(page < 0 || page > lastPage)
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
        if(id < 0 || id > auditionService.getMaxAuditionId())
            throw new AuditionNotFoundException();
        final ModelAndView mav = new ModelAndView("views/audition");
        Optional<Audition> audition = auditionService.getAuditionById(id);
        if (audition.isPresent()) {
            mav.addObject("audition", audition.get());
        } else {
            throw new AuditionNotFoundException();
        }

        mav.addObject("applications", applicationService.getAuditionApplications(id));
        return mav;
    }

    @RequestMapping(value = "/apply", method = {RequestMethod.POST})
    public ModelAndView apply(@Valid @ModelAttribute("applicationForm") final ApplicationForm applicationForm,
                              final BindingResult errors,
                              @RequestParam(required = true) final long id ){
        if (errors.hasErrors()) {
            return audition(applicationForm,id);
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> optionalUser = userService.findByEmail(auth.getName());
        User user = optionalUser.orElseThrow(UserNotFoundException::new);

        // TODO: FOTO NO FUNCIONA EN AUDITION-APPLICATION.HTML
        applicationService.apply(id, user,
                applicationForm.getMessage());

        return success();
    }

    @RequestMapping(value = "/newAudition", method = {RequestMethod.GET})
    public ModelAndView newAudition(@ModelAttribute("auditionForm") final AuditionForm auditionForm) {
        final ModelAndView mav = new ModelAndView("views/auditionForm");

        Set<Role> roleList = roleService.getAll();
        Set<Genre> genreList = genreService.getAll();
        List<Location> locationList = locationService.getAll();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> optionalUser = userService.findByEmail(auth.getName());
        User user = optionalUser.orElseThrow(UserNotFoundException::new);

        mav.addObject("roleList", roleList);
        mav.addObject("genreList", genreList);
        mav.addObject("locationList", locationList);
        mav.addObject("user",user);
        return mav;
    }

    @RequestMapping(value="/newAudition", method = {RequestMethod.POST})
    public ModelAndView postNewAudition(@Valid @ModelAttribute("auditionForm") final AuditionForm auditionForm,
                                        final BindingResult errors) {

        if(errors.hasErrors()) {
            return newAudition(auditionForm);
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> optionalUser = userService.findByEmail(auth.getName());
        User user = optionalUser.orElseThrow(UserNotFoundException::new);

        auditionService.create(auditionForm.toBuilder(user.getId()).
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
