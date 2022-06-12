package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.exceptions.*;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.form.*;
import ar.edu.itba.paw.service.AuthFacadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


@Controller
public class UserController {

    // TODO: ya no haria falta que le pida al servicio por los roles y generos favoritos al mostrar el perfil
    // por ejemplo, es mas, ya no harian falta esos metodos en el servicio.
    private final UserService userService;
    private final VerificationTokenService verificationTokenService;
    private final RoleService roleService;
    private final GenreService genreService;
    private final ApplicationService applicationService;
    private final AuthFacadeService authFacadeService;
    private final LocationService locationService;
    private final MembershipService membershipService;


    @Autowired
    public UserController(final UserService userService, final VerificationTokenService verificationTokenService,
                          final RoleService roleService, final GenreService genreService,
                          final ApplicationService applicationService,
                          final AuthFacadeService authFacadeService,
                          final LocationService locationService,
                          final MembershipService membershipService) {
        this.userService = userService;
        this.verificationTokenService = verificationTokenService;
        this.roleService = roleService;
        this.genreService = genreService;
        this.applicationService = applicationService;
        this.authFacadeService = authFacadeService;
        this.locationService = locationService;
        this.membershipService = membershipService;
    }

    @RequestMapping(value = {"/register","/registerBand", "/registerArtist"}, method = {RequestMethod.GET})
    public ModelAndView registerView(@ModelAttribute("userBandForm") final UserBandForm userBandForm,
                                     @ModelAttribute("userArtistForm") final UserArtistForm userArtistForm,
                                     boolean isBand) {
        ModelAndView mav = new ModelAndView("register");
        mav.addObject("isBand", isBand);
        return mav;
    }

    @RequestMapping(value = "/registerBand", method = {RequestMethod.POST})
    public ModelAndView registerBand(@Valid @ModelAttribute("userBandForm") final UserBandForm userBandForm,
                                     final BindingResult errors) {
        if (errors.hasErrors()) {
            ModelAndView returnRegister = registerView(userBandForm, new UserArtistForm(), true);
            returnRegister.addObject("userArtistForm", new UserArtistForm());
            return returnRegister;
        }

        User.UserBuilder user = new User.UserBuilder(userBandForm.getEmail(), userBandForm.getPassword(),
                userBandForm.getName(), userBandForm.isBand(), false);

        userService.create(user);

        return emailSent(userBandForm.getEmail());
    }

    @RequestMapping(value = "/registerArtist", method = {RequestMethod.POST})
    public ModelAndView registerArtist(@Valid @ModelAttribute("userArtistForm") final UserArtistForm userArtistForm,
                                       final BindingResult errors) {
        if (errors.hasErrors()) {
            ModelAndView returnRegister = registerView(new UserBandForm(), userArtistForm, false);
            returnRegister.addObject("userBandForm", new UserBandForm());
            return returnRegister;
        }

        User.UserBuilder user = new User.UserBuilder(userArtistForm.getEmail(), userArtistForm.getPassword(),
                userArtistForm.getName(), userArtistForm.isBand(), false)
                .surname(userArtistForm.getSurname());

        userService.create(user);

        return emailSent(userArtistForm.getEmail());
    }


    @RequestMapping(value = "/profile", method = {RequestMethod.GET})
    public ModelAndView profile() {
        ModelAndView mav = new ModelAndView("profile");
        User user = authFacadeService.getCurrentUser();
        mav.addObject("members", membershipService.getUserMembershipsPreview(user));
        int pendingMembershipsCount = membershipService.getPendingMembershipsCount(user);
        mav.addObject("pending", pendingMembershipsCount);
        return setAndReturnProfileViewData(user, null, mav);
    }

    @RequestMapping(value = "/user/{id}", method = {RequestMethod.GET})
    public ModelAndView profile(@PathVariable long id) {

        Optional<User> optionalUser = userService.getUserById(id);
        User userToVisit = optionalUser.orElseThrow(UserNotFoundException::new);
        User user = null;
        if(authFacadeService.isAuthenticated()) {

            user = authFacadeService.getCurrentUser();

            if(Objects.equals(user.getId(), userToVisit.getId()))
                return new ModelAndView("redirect:/profile");
        }

        ModelAndView mav = new ModelAndView("viewProfile");
        return setAndReturnProfileViewData(userToVisit, user, mav);
    }

    private ModelAndView setAndReturnProfileViewData(User userToVisit, User currentUser, ModelAndView mav) {

        mav.addObject("members", membershipService.getUserMembershipsPreview(userToVisit));
        mav.addObject("user", userToVisit);

        if (currentUser != null && currentUser.isBand()) {
            mav.addObject("canBeAddedToBand", membershipService.canBeAddedToBand(currentUser, userToVisit));
        } else {
            mav.addObject("canBeAddedToBand", false);
        }

        Set<Genre> preferredGenres = userService.getUserGenres(userToVisit);
        mav.addObject("preferredGenres", preferredGenres);

        Set<Role> roles = userService.getUserRoles(userToVisit);
        mav.addObject("roles", roles);

        Location location = userService.getUserLocation(userToVisit);
        mav.addObject("location", location);

        Set<SocialMedia> socialMedia = userService.getUserSocialMedia(userToVisit);
        mav.addObject("socialMedia", socialMedia);

        return mav;
    }

    @RequestMapping(value = "/user/{id}/addToBand", method = {RequestMethod.GET})
    public ModelAndView viewProfileAddToBand(@PathVariable long id,
                                             @ModelAttribute("membershipForm")
                                             final MembershipForm membershipForm) {
        ModelAndView mav = new ModelAndView("viewProfileAddToBand");

        Optional<User> optionalUser = userService.getUserById(id);
        User userToVisit = optionalUser.orElseThrow(UserNotFoundException::new);
//        TODO: Pasar validacion de usuario de tipo artista a servicio
        if (userToVisit.isBand()) {
            throw new UserNotFoundException("User is a band");
        }

        mav.addObject("user", userToVisit);
        Location location = userService.getUserLocation(userToVisit);
        mav.addObject("location", location);
        Set<Role> auditionRoles = roleService.getAll();
        mav.addObject("auditionRoles",auditionRoles);

        return mav;
    }

    @RequestMapping(value = "/user/{id}/addToBand", method = {RequestMethod.POST})
    public ModelAndView viewProfileAddToBand(@PathVariable long id,
                                      @Valid @ModelAttribute("membershipForm")
                                      final MembershipForm membershipForm,
                                      final BindingResult errors) {
        if (errors.hasErrors()) {
            return viewProfileAddToBand(id,membershipForm);
        }


        // TODO: membershipSuccess.jsp y selectApplicant.jsp

        membershipService.createMembershipInvite(new Membership.Builder(
                userService.getUserById(id).orElseThrow(UserNotFoundException::new),
                authFacadeService.getCurrentUser(),
                roleService.getRolesByNames(membershipForm.getRoles())).
                description(membershipForm.getDescription()).
                state(MembershipState.PENDING));

        return new ModelAndView("membershipSuccess");
    }



    @RequestMapping(value = "/profile/applications", method = {RequestMethod.GET})
    public ModelAndView applications(@RequestParam(value = "page", defaultValue = "1") int page,
                                     @RequestParam(value = "state", defaultValue = "PENDING") String state) {

        ModelAndView mav = new ModelAndView("profileApplications");
        User user = authFacadeService.getCurrentUser();
        List<Application> applications = applicationService.getMyApplicationsFiltered(user.getId(), page,
                ApplicationState.valueOf(state));
        int lastPage = applicationService.getTotalUserApplicationPagesFiltered(user.getId(), ApplicationState.valueOf(state));
        lastPage = lastPage == 0 ? 1 : lastPage;
        mav.addObject("artistApplications", applications);
        mav.addObject("currentPage", page);
        mav.addObject("lastPage", lastPage);
        return mav;
    }

    @RequestMapping( value = "/user/{userId}/profile-image", method = {RequestMethod.GET},
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @ResponseBody
    public byte[] profilePicture(@PathVariable(value = "userId") long userId) throws IOException {
        return userService.getProfilePicture(userId);
    }

    @RequestMapping(value = "/profile/editArtist", method = {RequestMethod.GET})
    public ModelAndView editProfile(@ModelAttribute("artistEditForm") final ArtistEditForm artistEditForm) {
        ModelAndView mav = new ModelAndView("editArtistProfile");
        return initializeEditProfile(mav,artistEditForm);
    }

    @RequestMapping(value = "/profile/editBand", method = {RequestMethod.GET})
    public ModelAndView editProfile(@ModelAttribute("bandEditForm") final BandEditForm bandEditForm) {
        ModelAndView mav = new ModelAndView("editBandProfile");
        return initializeEditProfile(mav,bandEditForm);
    }

    private ModelAndView initializeEditProfile(ModelAndView mav, UserEditForm editForm ) {
        User user = authFacadeService.getCurrentUser();
        Set<Role> roleList = roleService.getAll();
        Set<Genre> genreList = genreService.getAll();
        List<Location> locationList = locationService.getAll();
        Set<Role> userRoles = userService.getUserRoles(user);
        Set<Genre> userGenres = userService.getUserGenres(user);
        Location userLocation = userService.getUserLocation(user);
        Set<SocialMedia> socialMedia = userService.getUserSocialMedia(user);
        List<String> selectedRoles = userRoles.stream().map(Role::getName).collect(Collectors.toList());
        List<String> selectedGenres = userGenres.stream().map(Genre::getName).collect(Collectors.toList());
        String selectedLocation = userLocation == null ? null : userLocation.getName();
        editForm.initialize(user,selectedGenres,selectedRoles, socialMedia, selectedLocation);
        mav.addObject("user", user);
        mav.addObject("roleList", roleList);
        mav.addObject("genreList", genreList);
        mav.addObject("locationList", locationList);
        return mav;
    }

    @RequestMapping(value = "/profile/editArtist", method = {RequestMethod.POST})
    public ModelAndView postEditProfile(@Valid @ModelAttribute("artistEditForm")
                                        final ArtistEditForm artistEditForm,
                                        final BindingResult errors) {
        if (errors.hasErrors()) {
            return editProfile(artistEditForm);
        }

        User user = authFacadeService.getCurrentUser();

       userService.editUser(user.getId(), artistEditForm.getName(), artistEditForm.getSurname(), artistEditForm.getDescription(),
               artistEditForm.getMusicGenres(), artistEditForm.getLookingFor(),
               artistEditForm.getProfileImage().getBytes(), artistEditForm.getLocation());
       userService.updateSocialMedia(user,artistEditForm.getSocialMedia());
       userService.setAvailable(artistEditForm.getAvailable(), user);

        return new ModelAndView("redirect:/profile");
    }

    @RequestMapping(value = "/profile/editBand", method = {RequestMethod.POST})
    public ModelAndView postEditProfile(@Valid @ModelAttribute("bandEditForm")
                                        final BandEditForm bandEditForm,
                                        final BindingResult errors) {
        if (errors.hasErrors()) {
            return editProfile(bandEditForm);
        }

        User user = authFacadeService.getCurrentUser();

        userService.editUser(user.getId(), bandEditForm.getName(),null, bandEditForm.getDescription(),
                bandEditForm.getMusicGenres(), bandEditForm.getLookingFor(),
                bandEditForm.getProfileImage().getBytes(), bandEditForm.getLocation());
        userService.updateSocialMedia(user,bandEditForm.getSocialMedia());

        return new ModelAndView("redirect:/profile");
    }

    @RequestMapping(value = "/verify")
    public ModelAndView verify(@RequestParam final String token) {
        userService.verifyUser(token);
        return new ModelAndView("verified");
    }

    @RequestMapping(value = "/resetPassword", method = {RequestMethod.GET})
    public ModelAndView resetPassword(@ModelAttribute("resetPasswordForm")
                                      final ResetPasswordForm resetPasswordForm) {

        return new ModelAndView("resetPassword");
    }

    @RequestMapping(value = "/resetPassword", method = {RequestMethod.POST})
    public ModelAndView resetPassword(@Valid @ModelAttribute("resetPasswordForm")
                                      final ResetPasswordForm resetPasswordForm,
                                      final BindingResult errors) {
        if (errors.hasErrors()) {
           return resetPassword(resetPasswordForm);
        }
        userService.sendResetEmail(resetPasswordForm.getEmail());
        ModelAndView mav = new ModelAndView("resetPassword");
        mav.addObject("resetPasswordForm",resetPasswordForm);
        return resetEmailSent(resetPasswordForm.getEmail());
    }

    @RequestMapping(value = "/newPassword", method = {RequestMethod.GET})
    public ModelAndView newPassword(@RequestParam final String token,
                                    @ModelAttribute("newPasswordForm") final NewPasswordForm newPasswordForm) {

        verificationTokenService.isValid(token);
        ModelAndView mav = new ModelAndView("newPassword");
        mav.addObject("token",token);
        return mav;
    }

    @RequestMapping(value = "/newPassword", method = {RequestMethod.POST})
    public ModelAndView newPassword(@RequestParam final String token,
                                    @Valid @ModelAttribute("newPasswordForm") final NewPasswordForm newPasswordForm,
                                    final BindingResult errors) {

        if (errors.hasErrors()) {
            return newPassword(token, newPasswordForm);
        }

        userService.changePassword(token, newPasswordForm.getNewPassword());

        return new ModelAndView("redirect:/auditions");
    }

    @RequestMapping(value = "/resetEmailSent", method = {RequestMethod.GET})
    public ModelAndView resetEmailSent(String email) {
        ModelAndView emailSent = new ModelAndView("resetEmailSent");
        emailSent.addObject("email", email);
        return emailSent;
    }

    @RequestMapping(value = "/resetEmailSent", method = {RequestMethod.POST})
    public ModelAndView resendResetEmail(@RequestParam final String email) {
        userService.sendResetEmail(email);
        return resetEmailSent(email);
    }

    @RequestMapping(value = "/emailSent", method = {RequestMethod.GET})
    public ModelAndView emailSent(String email) {
        ModelAndView emailSent = new ModelAndView("emailSent");
        emailSent.addObject("email", email);
        return emailSent;
    }

    @RequestMapping(value = "/emailSent", method = {RequestMethod.POST})
    public ModelAndView resendEmail(@RequestParam final String email) {
        userService.resendUserVerification(email);
        return emailSent(email);
    }

    @RequestMapping(value = "/login", method = {RequestMethod.GET})
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/users/search", method = {RequestMethod.GET})
    public ModelAndView usersSearch(@RequestParam(value = "page", defaultValue = "1") int page,
                              @RequestParam(value = "query", defaultValue = "") String query,
                              @RequestParam(value = "genre", required = false) String[] genres,
                              @RequestParam(value = "role", required = false) String[] roles,
                              @RequestParam(value = "location", required = false) String[] locations) {

        ModelAndView mav = new ModelAndView("users");

        FilterOptions filter = new FilterOptions.FilterOptionsBuilder().
                withGenres(genres == null ? null : Arrays.asList(genres))
                .withRoles(roles == null ? null : Arrays.asList(roles))
                .withLocations(locations == null ? null : Arrays.asList(locations))
                .withTitle(query).build();
        initializeFilterOptions(mav);

        List<User> userList = userService.filter(filter,page);
        int lastPage = userService.getFilterTotalPages(filter);
        lastPage = lastPage == 0 ? 1 : lastPage;
        mav.addObject("userList", userList);
        mav.addObject("currentPage", page);
        mav.addObject("query", query);
        mav.addObject("lastPage", lastPage);
        return mav;
    }

    @RequestMapping(value = "/users", method = {RequestMethod.GET})
    public ModelAndView usersDiscover() {

        ModelAndView userDiscover = new ModelAndView("usersDiscover");

        initializeFilterOptions(userDiscover);

        return userDiscover;
    }


//    TODO: refactor this method
//    membershipService.getUserMemberships(currentUser,MembershipState.PENDING,page);
//    me da lo mismo si estoy en dos bandas distintas (JetsonMade y JackHarlow)
    /* metodo para ver las invitaciones de memberships que le mandaron a un artista */
    @RequestMapping(value = "/invites",  method = {RequestMethod.GET})
    public ModelAndView bandMembershipInvites(@RequestParam(value = "page", defaultValue = "1") int page) {
        ModelAndView mav = new ModelAndView("invites");
        User currentUser = authFacadeService.getCurrentUser();
        List<Membership> bandInvites = membershipService.getUserMemberships(currentUser,MembershipState.PENDING,page);
        int lastPage = membershipService.getTotalUserMembershipsPages(currentUser,MembershipState.PENDING);
        lastPage = lastPage == 0 ? 1 : lastPage;
        mav.addObject("user", currentUser);
        mav.addObject("invites", bandInvites);
        mav.addObject("pendingMembershipsCount", membershipService.getPendingMembershipsCount(currentUser));
        mav.addObject("currentPage", page);
        mav.addObject("lastPage", lastPage);
        return mav;
    }

    /* metodo para aceptar/rechazar la membership */
    @RequestMapping(value = "/invites/{membershipId}",  method = {RequestMethod.POST})
    public ModelAndView evaluateInvite(@PathVariable long membershipId,
                                       @RequestParam(value = "accept") boolean accept) {
        Membership membership = membershipService.getMembershipById(membershipId).orElseThrow(MembershipNotFoundException::new);
        // TODO: PODRIA RECIBIR EL STATE Y HCER UNVALUEOF
        if (accept) {
            membershipService.changeState(membership, MembershipState.ACCEPTED);
        } else {
            membershipService.changeState(membership, MembershipState.REJECTED);
        }

        return new ModelAndView("redirect:/profile");
    }

    @RequestMapping(value = "/profile/bandMembers",  method = {RequestMethod.GET})
    public ModelAndView bandMembers(@RequestParam(value = "page", defaultValue = "1") int page) {
        User band =  authFacadeService.getCurrentUser();
        return getBandMembers(page, band);
    }

    @RequestMapping(value = "/user/{id}/bandMembers",  method = {RequestMethod.GET})
    public ModelAndView viewBandMembers(@PathVariable long id,
                                        @RequestParam(value = "page", defaultValue = "1") int page) {
        User band =  userService.getUserById(id).orElseThrow(UserNotFoundException::new);
        if(band.getId().equals(authFacadeService.getCurrentUser().getId()))
            return new ModelAndView("redirect:/profile/bandMembers");
        return getBandMembers(page, band);
    }

    @RequestMapping(value = "/profile/bands",  method = {RequestMethod.GET})
    public ModelAndView artistBands(@RequestParam(value = "page", defaultValue = "1") int page) {
        User artist =  authFacadeService.getCurrentUser();
        return getArtistBands(page, artist);
    }

    @RequestMapping(value = "/user/{id}/bands",  method = {RequestMethod.GET})
    public ModelAndView viewArtistBands(@PathVariable long id,
                                        @RequestParam(value = "page", defaultValue = "1") int page) {
        User artist =  userService.getUserById(id).orElseThrow(UserNotFoundException::new);
        if(artist.getId().equals(authFacadeService.getCurrentUser().getId()))
            return new ModelAndView("redirect:/profile/bands");
        return getArtistBands(page, artist);
    }

    private void initializeFilterOptions(ModelAndView mav) {
        Set<Role> roleList = roleService.getAll();
        Set<Genre> genreList = genreService.getAll();
        List<Location> locationList = locationService.getAll();
        mav.addObject("roleList", roleList.stream().map(Role::getName).collect(Collectors.toList()));
        mav.addObject("genreList", genreList.stream().map(Genre::getName).collect(Collectors.toList()));
        mav.addObject("locationList", locationList.stream().map(Location::getName).collect(Collectors.toList()));
    }

    @RequestMapping(value = "/profile/editMembership/{membershipId}", method = {RequestMethod.GET})
    public ModelAndView editMembership(@ModelAttribute("membershipForm") final MembershipForm membershipForm,
                                     @PathVariable long membershipId) {

        User user = authFacadeService.getCurrentUser();

        Membership membership = membershipService.getMembershipById(membershipId).orElseThrow(MembershipNotFoundException::new);

        if(!Objects.equals(user.getId(), membership.getBand().getId()))
            throw new BandNotOwnedException();

        ModelAndView mav = new ModelAndView("editMembership");

        Set<Role> roleList = roleService.getAll();

        mav.addObject("roleList", roleList);
        mav.addObject("membershipId", membership.getId());
        mav.addObject("artistName", membership.getArtist().getName());
        mav.addObject("artistSurname", membership.getArtist().getSurname());

        membershipForm.setDescription(membership.getDescription());

        List<String> selectedRoles = membership.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        membershipForm.setRoles(selectedRoles);

        return mav;
    }

    @RequestMapping(value = "/profile/editMembership/{membershipId}", method = {RequestMethod.POST})
    public ModelAndView postEditMembership(@Valid @ModelAttribute("membershipForm") final MembershipForm membershipForm,
                                      final BindingResult errors, @PathVariable long membershipId) {

        if(errors.hasErrors()) {
            return editMembership(membershipForm, membershipId);
        }

        membershipService.editMembershipById(membershipForm.getDescription(),roleService.getRolesByNames(membershipForm.getRoles()), membershipId);

        return new ModelAndView("redirect:/profile/bandMembers");
    }


    @RequestMapping(value = "/profile/deleteMembership/{membershipId}", method = {RequestMethod.POST})
    public ModelAndView deleteMembership(@PathVariable long membershipId) {
        membershipService.deleteMembership(membershipId);
        return new ModelAndView("redirect:/profile");
    }

    private ModelAndView getBandMembers(int page, User band) {
        List<Membership> members = membershipService.getUserMemberships(
                band,
                MembershipState.ACCEPTED,
                page);
        ModelAndView mav = new ModelAndView("bandMembers");
        mav.addObject("bandName", band.getName());
        return getMembershipMav(mav, band, page, members);
    }

    private ModelAndView getArtistBands(int page, User artist) {
        List<Membership> members = membershipService.getUserMemberships(
                artist,
                MembershipState.ACCEPTED,
                page);
        ModelAndView mav = new ModelAndView("playsIn");
        mav.addObject("artistName", artist.getName());
        mav.addObject("artistSurname", artist.getSurname());
        return getMembershipMav(mav, artist, page, members);
    }

    private ModelAndView getMembershipMav(ModelAndView mav, User user, int page, List<Membership> members) {
        if(user.getId().equals(authFacadeService.getCurrentUser().getId())){
            mav.addObject("isPropietary", true);
        } else {
            mav.addObject("isPropietary", false);
        }
        int lastPage = membershipService.getTotalUserMembershipsPages(user, MembershipState.ACCEPTED);
        lastPage = lastPage == 0 ? 1 : lastPage;
        mav.addObject("members", members);
        mav.addObject("currentPage", page);
        mav.addObject("lastPage", lastPage);
        return mav;
    }

}
