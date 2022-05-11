package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.*;
import ar.edu.itba.paw.model.exceptions.AuditionNotOwnedException;
import ar.edu.itba.paw.model.exceptions.AuditionNotFoundException;
import ar.edu.itba.paw.model.exceptions.LocationNotFoundException;
import ar.edu.itba.paw.model.exceptions.UserNotFoundException;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.form.ApplicationForm;
import ar.edu.itba.paw.webapp.form.AuditionForm;
import ar.edu.itba.paw.service.AuthFacadeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final AuthFacadeService authFacadeService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditionsController.class);

    @Autowired
    public AuditionsController(final AuditionService auditionService,
                               final GenreService genreService, final LocationService locationService,
                               final RoleService roleService, final UserService userService,
                               final ApplicationService applicationService,
                               final AuthFacadeService authFacadeService) {
        this.auditionService = auditionService;
        this.roleService = roleService;
        this.genreService = genreService;
        this.locationService = locationService;
        this.userService = userService;
        this.applicationService = applicationService;
        this.authFacadeService = authFacadeService;
    }

    @RequestMapping(value = "/", method = {RequestMethod.GET})
    public ModelAndView home() {
        if (authFacadeService.isAuthenticated()) {
            return new ModelAndView("redirect:/auditions");
        }
        return new ModelAndView("redirect:/welcome");
    }

    @RequestMapping(value = "/auditions", method = {RequestMethod.GET})
    public ModelAndView auditions( @RequestParam(value = "page", defaultValue = "1") int page) {
        final ModelAndView mav = new ModelAndView("auditions");
        List<Audition> auditionList = auditionService.getAll(page);
        int lastPage = auditionService.getTotalPages();
        lastPage = lastPage == 0 ? 1 : lastPage;
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
        initializeFilterOptions(mav);
        List<Audition> auditionList = auditionService.filter(filter,page);
        int lastPage = auditionService.getFilterTotalPages(filter);
        lastPage = lastPage == 0 ? 1 : lastPage;
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

        final ModelAndView mav = new ModelAndView("audition");
        Audition audition = auditionService.getAuditionById(id).orElseThrow(AuditionNotFoundException::new);
        User user = authFacadeService.getCurrentUser();
        User band = userService.getUserById(audition.getBandId()).orElseThrow(UserNotFoundException::new);
        mav.addObject("isOwner", audition.getBandId() == user.getId());
        mav.addObject("audition", audition);
        mav.addObject("user",band);
        return mav;
    }

    @RequestMapping(value = "/auditions/{id}/applicants", method = {RequestMethod.GET})
    public ModelAndView applicants(@PathVariable long id,
                                   @RequestParam(value = "page", defaultValue = "1") int page,
                                   @RequestParam(value = "state", defaultValue = "1") int state) {
        ModelAndView mav = new ModelAndView("applicants");
        List<Application> applications = applicationService.getAuditionApplicationsByState(id, ApplicationState.values()[state], page);
        Audition aud = auditionService.getAuditionById(id).orElseThrow(AuditionNotFoundException::new);
        int lastPage = applicationService.getTotalAuditionApplicationByStatePages(id, ApplicationState.values()[state]);
        lastPage = lastPage == 0 ? 1 : lastPage;
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
                              @RequestParam final long id ){
        if (errors.hasErrors()) {
            return audition(applicationForm,id);
        }

        User user = authFacadeService.getCurrentUser();

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

        User user = authFacadeService.getCurrentUser();

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

        User user = authFacadeService.getCurrentUser();

        auditionService.create(auditionForm.toBuilder(user.getId()).
                location(locationService.getLocationByName(auditionForm.getLocation()).orElseThrow(LocationNotFoundException::new)).
                lookingFor(roleService.getRolesByNames(auditionForm.getLookingFor())).
                musicGenres(genreService.getGenresByNames(auditionForm.getMusicGenres()))
        );

        return new ModelAndView("redirect:/auditions");
    }

    @RequestMapping(value = "/success", method = {RequestMethod.GET})
    public ModelAndView success() {
        return new ModelAndView("successMsg");
    }

    @RequestMapping(value = "/profile/deleteAudition/{id}", method = {RequestMethod.POST})
    public ModelAndView deleteAudition(@PathVariable long id) {
        auditionService.deleteAuditionById(id);
        return new ModelAndView("redirect:/profile/auditions");
    }

    @RequestMapping(value = "/profile/editAudition/{id}", method = {RequestMethod.GET})
    public ModelAndView editAudition(@ModelAttribute("auditionForm") final AuditionForm auditionForm, @PathVariable long id) {

        User user = authFacadeService.getCurrentUser();

        Audition audition = auditionService.getAuditionById(id).orElseThrow(AuditionNotFoundException::new);

        if(user.getId() != audition.getBandId())
            throw new AuditionNotOwnedException();

        ModelAndView mav = new ModelAndView("editAudition");

        Set<Role> roleList = roleService.getAll();
        Set<Genre> genreList = genreService.getAll();
        List<Location> locationList = locationService.getAll();

        mav.addObject("roleList", roleList);
        mav.addObject("genreList", genreList);
        mav.addObject("locationList", locationList);
        mav.addObject("auditionId", id);

        auditionForm.setTitle(audition.getTitle());
        auditionForm.setDescription(audition.getDescription());
        auditionForm.setLocation(audition.getLocation().getName());

        List<String> selectedRoles = audition.getLookingFor().stream().map(Role::getName).collect(Collectors.toList());
        auditionForm.setLookingFor(selectedRoles);

        List<String> selectedGenres = audition.getMusicGenres().stream().map(Genre::getName).collect(Collectors.toList());
        auditionForm.setMusicGenres(selectedGenres);

        return mav;
    }


    @RequestMapping(value="/profile/editAudition/{id}", method = {RequestMethod.POST})
    public ModelAndView postEditAudition(@Valid @ModelAttribute("auditionForm") final AuditionForm auditionForm,
                                         final BindingResult errors, @PathVariable long id) {

        User user = authFacadeService.getCurrentUser();

        if(errors.hasErrors()) {
            return editAudition(auditionForm,id);
        }

        auditionService.editAuditionById(auditionForm.toBuilder(user.getId()).
                location(locationService.getLocationByName(auditionForm.getLocation()).orElseThrow(LocationNotFoundException::new)).
                lookingFor(roleService.getRolesByNames(auditionForm.getLookingFor())).
                musicGenres(genreService.getGenresByNames(auditionForm.getMusicGenres())), id);

        String redirect = "redirect:/auditions/" + id;

        return new ModelAndView(redirect);
    }

    @RequestMapping(value = "/profile/auditions", method = {RequestMethod.GET})
    public ModelAndView profileAuditions(@RequestParam(value = "page", defaultValue = "1") int page) {
        ModelAndView mav = new ModelAndView("profileAuditions");

        User user = authFacadeService.getCurrentUser();
        List<Audition> auditionList = auditionService.getBandAuditions(user.getId(), page);
        int lastPage = auditionService.getTotalBandAuditionPages(user.getId());
        lastPage = lastPage == 0 ? 1 : lastPage;
        mav.addObject("userName", user.getName());
        mav.addObject("userId", user.getId());
        mav.addObject("auditionList", auditionList);
        mav.addObject("currentPage", page);
        mav.addObject("lastPage", lastPage);
        return mav;
    }

    @RequestMapping(value = "/auditions/{auditionId}", method = {RequestMethod.POST})
    public ModelAndView evaluateApplication(@PathVariable long auditionId,
                                            @RequestParam(value = "accept") boolean accept,
                                            @RequestParam(value = "userId") long userId) {
        if (accept) {
            applicationService.accept(auditionId, userId);
        } else {
            applicationService.reject(auditionId, userId);
        }

        return new ModelAndView("redirect:/auditions/" + auditionId + "/applicants");
    }
}
