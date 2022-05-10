package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.exceptions.AuditionNotOwnedException;
import ar.edu.itba.paw.AuditionFilter;
import ar.edu.itba.paw.persistence.Genre;
import ar.edu.itba.paw.persistence.Location;
import ar.edu.itba.paw.persistence.Role;
import ar.edu.itba.paw.persistence.User;
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
import java.util.stream.Collectors;

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
        final ModelAndView mav = new ModelAndView("auditions");
        int lastPage = auditionService.getTotalPages();
        if(lastPage == 0)
            lastPage = 1;
        if(page < 0 || page > lastPage)
            return new ModelAndView("errors/404");

        List<Audition> auditionList = auditionService.getAll(page);
        mav.addObject("auditionList", auditionList);
        mav.addObject("currentPage", page);
        mav.addObject("lastPage", lastPage);

        initializeFilterOptions(mav);
        return mav;
    }

    @RequestMapping(value = "/search", method = {RequestMethod.GET})
    public ModelAndView search( @RequestParam(value = "page", defaultValue = "1") int page,
                                @RequestParam(value = "query", defaultValue = "") String query,
                                @RequestParam(value = "genre", required = false) String[] genres,
                                @RequestParam(value = "role", required = false) String[] roles,
                                @RequestParam(value = "location", required = false) String[] locations,
                                @RequestParam(value = "order", defaultValue = "desc") String order) {
        final ModelAndView mav = new ModelAndView("search");

        AuditionFilter filter = new AuditionFilter.AuditionFilterBuilder().
                withGenres(genres == null ? null : Arrays.asList(genres))
                .withRoles(roles == null ? null : Arrays.asList(roles))
                .withLocations(locations == null ? null : Arrays.asList(locations))
                .withTitle(query).withOrder(order).build();
        int lastPage = auditionService.getFilterTotalPages(filter);
        if(lastPage == 0)
            lastPage = 1;
        if(page < 0 || page > lastPage)
            return new ModelAndView("errors/404");
        initializeFilterOptions(mav);
        List<Audition> auditionList = auditionService.filter(filter,page);
        mav.addObject("auditionList", auditionList);
        mav.addObject("currentPage", page);
        mav.addObject("query", query);
        mav.addObject("lastPage", lastPage);
        return mav;
    }

    private void initializeFilterOptions(ModelAndView mav) {
        Set<Role> roleList = roleService.getAll();
        Set<Genre> genreList = genreService.getAll();
        List<Location> locationList = locationService.getAll();
        mav.addObject("roleList", roleList.stream().map(Role::getName).collect(Collectors.toList()));
        mav.addObject("genreList", genreList.stream().map(Genre::getName).collect(Collectors.toList()));
        mav.addObject("locationList", locationList.stream().map(Location::getName).collect(Collectors.toList()));
    }

    @RequestMapping(value = "/auditions/{id}", method = {RequestMethod.GET})
    public ModelAndView audition(@ModelAttribute("applicationForm") final ApplicationForm applicationForm,
                                 @PathVariable long id) {
        if(id < 0 || id > auditionService.getMaxAuditionId())
            throw new AuditionNotFoundException();
        final ModelAndView mav = new ModelAndView("audition");
        Audition audition = auditionService.getAuditionById(id).orElseThrow(AuditionNotFoundException::new);
        User user = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);
        User band = userService.getUserById(audition.getBandId()).orElseThrow(UserNotFoundException::new);
        mav.addObject("isOwner", audition.getBandId() == user.getId());
        mav.addObject("audition", audition);
        mav.addObject("user",band);
        return mav;
    }

    // TODO: MEJORAR EL CHEQUEO DE PAGE < 0 QUE ESTA MAL
    @RequestMapping(value = "/auditions/{id}/applicants", method = {RequestMethod.GET})
    public ModelAndView applicants(@PathVariable long id,
                                   @RequestParam(value = "page", defaultValue = "1") int page,
                                   @RequestParam(value = "state", defaultValue = "pending") String state) {
        ModelAndView mav = new ModelAndView("applicants");

        int lastPage = applicationService.getTotalAuditionApplicationByStatePages(id,ApplicationState.valueOf(state.toUpperCase()));
        List<Application> applications = applicationService.getAuditionApplicationsByState(id, ApplicationState.valueOf(state.toUpperCase()), page);
        Audition aud = auditionService.getAuditionById(id).orElseThrow(AuditionNotFoundException::new);
        if(lastPage == 0)
            lastPage = 1;
        if(page < 0 || page > lastPage)
            return new ModelAndView("errors/404");
        mav.addObject("id",id);
        mav.addObject("auditionTitle", aud.getTitle());
        mav.addObject("applications", applications);
        mav.addObject("currentPage", page);
        mav.addObject("lastPage", lastPage);
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

        if(applicationService.apply(id, user, applicationForm.getMessage()))
            return success();
        return new ModelAndView("applicationFailed");
    }


    @RequestMapping(value = "/newAudition", method = {RequestMethod.GET})
    public ModelAndView newAudition(@ModelAttribute("auditionForm") final AuditionForm auditionForm) {
        final ModelAndView mav = new ModelAndView("auditionForm");

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

        return new ModelAndView("redirect:/auditions");
    }

    @RequestMapping(value = "/success", method = {RequestMethod.GET})
    public ModelAndView success() {
        return new ModelAndView("successMsg");
    }

    @RequestMapping(value = "/profile/deleteAudition/{id}", method = {RequestMethod.POST})
    public ModelAndView deleteAudition(@PathVariable long id) {
        if(id < 0 || id > auditionService.getMaxAuditionId())
            throw new AuditionNotFoundException();
        auditionService.deleteAuditionById(id);

        return new ModelAndView("redirect:/profile/auditions");
    }


    //TODO ESTA BIEN ESTO?
    @RequestMapping(value = "/profile/deleteAudition/{id}", method = {RequestMethod.GET})
    public ModelAndView getDeleteAudition(@PathVariable long id) {
        return new ModelAndView("redirect:/profile");
    }


    //TODO CODIGO REPETIDO MODULARIZAR
    @RequestMapping(value = "/profile/editAudition/{id}", method = {RequestMethod.GET})
    public ModelAndView editAudition(@ModelAttribute("auditionForm") final AuditionForm auditionForm, @PathVariable long id) {

        // TODO : es necesario este if? sino con el else de abajo seria suficiente creo
        if(id < 0 || id > auditionService.getMaxAuditionId())
            throw new AuditionNotFoundException();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> optionalUser = userService.findByEmail(auth.getName());
        User user = optionalUser.orElseThrow(UserNotFoundException::new);

        Optional<Audition> audition = auditionService.getAuditionById(id);

        if(!audition.isPresent())
            throw new AuditionNotFoundException();
        else if(user.getId() != audition.get().getBandId())
            throw new AuditionNotOwnedException();

        ModelAndView mav = new ModelAndView("editAudition");

        Set<Role> roleList = roleService.getAll();
        Set<Genre> genreList = genreService.getAll();
        List<Location> locationList = locationService.getAll();

        mav.addObject("roleList", roleList);
        mav.addObject("genreList", genreList);
        mav.addObject("locationList", locationList);
        mav.addObject("auditionId", id);

        auditionForm.setTitle(audition.get().getTitle());
        auditionForm.setDescription(audition.get().getDescription());
        auditionForm.setLocation(audition.get().getLocation().getName());

        List<String> selectedRoles = audition.get().getLookingFor().stream().map(Role::getName).collect(Collectors.toList());
        auditionForm.setLookingFor(selectedRoles);

        List<String> selectedGenres = audition.get().getMusicGenres().stream().map(Genre::getName).collect(Collectors.toList());
        auditionForm.setMusicGenres(selectedGenres);

        return mav;
    }

    //TODO CODIGO REPETIDO
    @RequestMapping(value="/profile/editAudition/{id}", method = {RequestMethod.POST})
    public ModelAndView postEditAudition(@Valid @ModelAttribute("auditionForm") final AuditionForm auditionForm,
                                         final BindingResult errors, @PathVariable long id) {

        if(id < 0 || id > auditionService.getMaxAuditionId())
            throw new AuditionNotFoundException();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> optionalUser = userService.findByEmail(auth.getName());
        User user = optionalUser.orElseThrow(UserNotFoundException::new);

        if(errors.hasErrors()) {
            return editAudition(auditionForm,id);
        }

        auditionService.editAuditionById(auditionForm.toBuilder(user.getId()).
                location(locationService.getLocationByName(auditionForm.getLocation()).orElseThrow(LocationNotFoundException::new)).
                lookingFor(roleService.validateAndReturnRoles(auditionForm.getLookingFor())).
                musicGenres(genreService.validateAndReturnGenres(auditionForm.getMusicGenres())), id);

        String redirect = "redirect:/auditions/" + id;

        return new ModelAndView(redirect);
    }

    @RequestMapping(value = "/profile/auditions", method = {RequestMethod.GET})
    public ModelAndView profileAuditions(@RequestParam(value = "page", defaultValue = "1") int page) {
        ModelAndView mav = new ModelAndView("profileAuditions");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> optionalUser = userService.findByEmail(auth.getName());
        User user = optionalUser.orElseThrow(UserNotFoundException::new);

        int lastPage = auditionService.getTotalBandAuditionPages(user.getId());
        if(lastPage == 0)
            lastPage = 1;
        if(page < 0 || page > lastPage)
            return new ModelAndView("errors/404");

        List<Audition> auditionList = auditionService.getBandAuditions(user.getId(), page);


        mav.addObject("userName", user.getName());
        mav.addObject("userId", user.getId());
        mav.addObject("auditionList", auditionList);
        mav.addObject("currentPage", page);
        mav.addObject("lastPage", lastPage);
        return mav;
    }

    @RequestMapping(value = "/auditions/{auditionId}", method = {RequestMethod.POST})
    public ModelAndView acceptApplication(@PathVariable long auditionId,
                                          @RequestParam(value = "accept") boolean accept,
                                          @RequestParam(value = "userId") long userId) {
        if(auditionId < 0 || auditionId > auditionService.getMaxAuditionId())
            throw new AuditionNotFoundException();

        if (accept) {
            applicationService.accept(auditionId, userId);
        } else {
            applicationService.reject(auditionId, userId);
        }

        return new ModelAndView("redirect:/auditions/" + auditionId + "/applicants");
    }
}
