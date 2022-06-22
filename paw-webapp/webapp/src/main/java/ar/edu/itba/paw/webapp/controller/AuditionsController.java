package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.exceptions.*;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.form.ApplicationForm;
import ar.edu.itba.paw.webapp.form.AuditionForm;
import ar.edu.itba.paw.service.AuthFacadeService;
import ar.edu.itba.paw.webapp.form.MembershipForm;
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
    private final MembershipService membershipService;

    @Autowired
    public AuditionsController(final AuditionService auditionService,
                               final GenreService genreService, final LocationService locationService,
                               final RoleService roleService, final UserService userService,
                               final ApplicationService applicationService,
                               final AuthFacadeService authFacadeService,
                               final MembershipService membershipService) {
        this.auditionService = auditionService;
        this.roleService = roleService;
        this.genreService = genreService;
        this.locationService = locationService;
        this.userService = userService;
        this.applicationService = applicationService;
        this.authFacadeService = authFacadeService;
        this.membershipService = membershipService;
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

    @RequestMapping(value = "/auditions/search", method = {RequestMethod.GET})
    public ModelAndView search( @RequestParam(value = "page", defaultValue = "1") int page,
                                @RequestParam(value = "query", defaultValue = "") String query,
                                @RequestParam(value = "genre", required = false) String[] genres,
                                @RequestParam(value = "role", required = false) String[] roles,
                                @RequestParam(value = "location", required = false) String[] locations,
                                @RequestParam(value = "order", defaultValue = "desc") String order) {
        final ModelAndView mav = new ModelAndView("search");

        FilterOptions filter = new FilterOptions.FilterOptionsBuilder().
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
        Audition audition = auditionService.getAuditionById(id);
        User user = authFacadeService.getCurrentUser();
        User band = userService.getUserById(audition.getBand().getId()).orElseThrow(UserNotFoundException::new);
        boolean alreadyApplied = applicationService.alreadyApplied(id, user.getId());
        mav.addObject("isOwner", Objects.equals(audition.getBand().getId(), user.getId()));
        mav.addObject("audition", audition);
        mav.addObject("user",band);
        mav.addObject("alreadyApplied", alreadyApplied);
        mav.addObject("canBeAddedToBand", !membershipService.isInBand(band, user));
        return mav;
    }

    @RequestMapping(value = "/auditions/{id}/applicants", method = {RequestMethod.GET})
    public ModelAndView applicants(@PathVariable long id,
                                   @RequestParam(value = "page", defaultValue = "1") int page,
                                   @RequestParam(value = "state", defaultValue = "PENDING") String state) {
        ModelAndView mav = new ModelAndView("applicants");
        List<Application> applications = applicationService.getAuditionApplicationsByState(id, ApplicationState.valueOf(state), page);
        Audition aud = auditionService.getAuditionById(id);

        //TODO ESTO ES MUY MALO MIRAR
        ArrayList<Boolean> isInBandArray = new ArrayList<>();
        if(Objects.equals(state, ApplicationState.ACCEPTED.getState())) {
            for(Application application : applications) {
                isInBandArray.add(membershipService.isInBand(application.getAudition().getBand(), application.getApplicant()));
            }
            mav.addObject("isInBand", isInBandArray);
        }

        int lastPage = applicationService.getTotalAuditionApplicationByStatePages(id, ApplicationState.valueOf(state));
        lastPage = lastPage == 0 ? 1 : lastPage;
        mav.addObject("id",id);
        mav.addObject("auditionTitle", aud.getTitle());
        mav.addObject("auditionId", aud.getId());
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

        auditionService.create(auditionForm.toBuilder(user).
                location(locationService.getLocationByName(auditionForm.getLocation()).orElseThrow(LocationNotFoundException::new)).
                lookingFor(roleService.getRolesByNames(auditionForm.getLookingFor())).
                musicGenres(genreService.getGenresByNames(auditionForm.getMusicGenres()))
        );

        return new ModelAndView("auditionSuccess");
    }

    @RequestMapping(value = "/success", method = {RequestMethod.GET})
    public ModelAndView success() {
        return new ModelAndView("successMsg");
    }

    @RequestMapping(value = "/profile/closeAudition/{id}", method = {RequestMethod.POST})
    public ModelAndView closeAudition(@PathVariable long id) {
        applicationService.closeApplicationsByAuditionId(id);
        auditionService.closeAuditionById(id);
        return new ModelAndView("redirect:/profile/auditions");
    }

    @RequestMapping(value = "/profile/editAudition/{id}", method = {RequestMethod.GET})
    public ModelAndView editAudition(@ModelAttribute("auditionForm") final AuditionForm auditionForm, @PathVariable long id) {

        User user = authFacadeService.getCurrentUser();

        Audition audition = auditionService.getAuditionById(id);

        if(!Objects.equals(user.getId(), audition.getBand().getId()))
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

        auditionService.editAuditionById(auditionForm.toBuilder(user).
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

        List<Audition> auditionList = auditionService.getBandAuditions(user, page);

        int lastPage = auditionService.getTotalBandAuditionPages(user);
        lastPage = lastPage == 0 ? 1 : lastPage;
        mav.addObject("userName", user.getName());
        mav.addObject("userId", user.getId());
        mav.addObject("auditionList", auditionList);
        mav.addObject("currentPage", page);
        mav.addObject("lastPage", lastPage);
        mav.addObject("isPropietary", true);
        return mav;
    }
    @RequestMapping(value = "/bandAuditions/{bandId}", method = {RequestMethod.GET})
    public ModelAndView profilePublicAuditions(@PathVariable long bandId, @RequestParam(value = "page", defaultValue = "1") int page) {
        ModelAndView mav = new ModelAndView("profileAuditions");

        User user = userService.getUserById(bandId).orElseThrow(UserNotFoundException::new);
        List<Audition> auditionList = auditionService.getBandAuditions(user, page);
        int lastPage = auditionService.getTotalBandAuditionPages(user);
        lastPage = lastPage == 0 ? 1 : lastPage;
        mav.addObject("userName", user.getName());
        mav.addObject("userId", user.getId());
        mav.addObject("auditionList", auditionList);
        mav.addObject("currentPage", page);
        mav.addObject("lastPage", lastPage);
        if(user.getId().equals(authFacadeService.getCurrentUser().getId())){
            mav.addObject("isPropietary", true);

        }else{
            mav.addObject("isPropietary", false);
        }

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


    /* estos metodos (metodo 1) son para SELECCIONAR un aplicante y crear la membresía ya ACEPTADA porque pasó la audicion */
    @RequestMapping(value = "/auditions/{id}/applicants/select/{applicationId}", method = {RequestMethod.GET})
    public ModelAndView select(@PathVariable long id,
                               @PathVariable long applicationId,
                               @ModelAttribute("membershipForm") final MembershipForm membershipForm) {
        ModelAndView mav = new ModelAndView("selectApplicant");
        Application application = applicationService.getAcceptedApplicationById(id,applicationId).orElseThrow(ApplicationNotFoundException::new);
        Set<Role> auditionRoles = application.getAudition().getLookingFor();
        String auditionTitle = application.getAudition().getTitle();

        mav.addObject("applicant",application.getApplicant());
        mav.addObject("auditionRoles",auditionRoles);
        mav.addObject("auditionTitle", auditionTitle);
        mav.addObject("applicantName", application.getApplicant().getName());
        mav.addObject("applicantSurname", application.getApplicant().getSurname());
        mav.addObject("applicantId", application.getApplicant().getId());
        mav.addObject("auditionId", application.getAudition().getId());
        mav.addObject("band", application.getAudition().getBand());
        return mav;
    }

    @RequestMapping(value = "/auditions/{id}/applicants/select/{applicationId}", method = {RequestMethod.POST})
    public ModelAndView select(@PathVariable long id,
                               @PathVariable long applicationId,
                               @Valid @ModelAttribute("membershipForm") final MembershipForm membershipForm,
                               final BindingResult errors) {

        if (errors.hasErrors()) {
            return select(id,applicationId,membershipForm);
        }
        Application application = applicationService.getApplicationById(id,applicationId).orElseThrow(ApplicationNotFoundException::new);

        if(membershipService.isInBand(application.getAudition().getBand(), application.getApplicant())) {
            return new ModelAndView("redirect: /profile");
        }

        membershipService.createMembershipByApplication(new Membership.Builder(application.getApplicant(),
                application.getAudition().getBand())
                        .description(membershipForm.getDescription())
                        .roles(roleService.getRolesByNames(membershipForm.getRoles())) ,
                application.getAudition().getId());
        return new ModelAndView("membershipSuccess");
    }

}
