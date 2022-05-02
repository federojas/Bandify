package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.persistence.Genre;
import ar.edu.itba.paw.persistence.Role;
import ar.edu.itba.paw.persistence.Audition;
import ar.edu.itba.paw.persistence.User;
import ar.edu.itba.paw.model.exceptions.UserNotFoundException;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.form.*;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.util.*;

@Controller
public class UserController {

    private final UserService userService;
    private final VerificationTokenService verificationTokenService;
    private final AuditionService auditionService;
    private final RoleService roleService;
    private final GenreService genreService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService, VerificationTokenService verificationTokenService,
                          AuditionService auditionService, RoleService roleService, GenreService genreService) {
        this.userService = userService;
        this.verificationTokenService = verificationTokenService;
        this.auditionService = auditionService;
        this.roleService = roleService;
        this.genreService = genreService;
    }

    @RequestMapping(value = {"/register","/registerBand", "/registerArtist"},
            method = {RequestMethod.GET})
    public ModelAndView registerView(@ModelAttribute("userBandForm") final UserBandForm userBandForm,
                                     @ModelAttribute("userArtistForm") final UserArtistForm userArtistForm,
                                     boolean isBand) {
        ModelAndView mav = new ModelAndView("views/register");
        mav.addObject("isBand", isBand);
        mav.addObject("userEmailAlreadyExists", false);
        return mav;
    }

    //TODO: MODULARIZAR CODIGO REPETIDO EN REGISTERS
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

    //TODO: MODULARIZAR CODIGO REPETIDO EN REGISTERS
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
        ModelAndView mav = new ModelAndView("views/profile");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> optionalUser = userService.findByEmail(auth.getName());
        User user = optionalUser.orElseThrow(UserNotFoundException::new);
        mav.addObject("user", user);
        return mav;
    }

    @RequestMapping(value = "/profile/auditions", method = {RequestMethod.GET})
    public ModelAndView profileAuditions(@RequestParam(value = "page", defaultValue = "1") int page) {
        ModelAndView mav = new ModelAndView("views/profileAuditions");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> optionalUser = userService.findByEmail(auth.getName());
        User user = optionalUser.orElseThrow(UserNotFoundException::new);

        int lastPage = auditionService.getTotalBandAuditionPages(user.getId());
        if(lastPage == 0)
            lastPage = 1;
        if(page < 0 || page > lastPage)
            return new ModelAndView("errors/404");

        List<Audition> auditionList = auditionService.getBandAuditions(user.getId(), page);
        mav.addObject("auditionList", auditionList);
        mav.addObject("currentPage", page);
        mav.addObject("lastPage", lastPage);
        return mav;
    }

    @RequestMapping( value = "/profile/profile-image/{userId}", method = {RequestMethod.GET})
    public void profilePicture(@PathVariable(value = "userId") Long userId,
                               HttpServletResponse response) throws IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> optionalUser = userService.findByEmail(auth.getName());
        User user = optionalUser.orElseThrow(UserNotFoundException::new);

        Optional<byte[]> image = userService.getProfilePictureByUserId(user.getId());
        InputStream stream;
        if(!image.isPresent()) {
            File file;
            if(user.isBand())
                file = ResourceUtils.getFile("classpath:images/band.jpg");
            else
                file = ResourceUtils.getFile("classpath:images/artist.png");

            InputStream fileStream = new FileInputStream(file);

            byte[] defaultImage = IOUtils.toByteArray(Objects.requireNonNull(fileStream));

            stream = new ByteArrayInputStream(defaultImage);
        } else
            stream = new ByteArrayInputStream(image.get());

        IOUtils.copy(stream, response.getOutputStream());
    }

    //TODO: MODULARIZAR CODIGO REPETIDO EN AUTH USER
    @RequestMapping(value = "/profile/edit", method = {RequestMethod.GET})
    public ModelAndView editProfile(@ModelAttribute("userEditForm") final UserEditForm userEditForm) {
        ModelAndView mav = new ModelAndView("views/editProfile");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> optionalUser = userService.findByEmail(auth.getName());
        User user = optionalUser.orElseThrow(UserNotFoundException::new);

        Set<Role> roleList = roleService.getAll();
        Set<Genre> genreList = genreService.getAll();
        Set<Role> userRoles = roleService.getUserRoles(user.getId());
        Set<Genre> userGenres = genreService.getUserGenres(user.getId());

        genreList.removeAll(userGenres);
        roleList.removeAll(userRoles);

        mav.addObject("userId", user.getId());
        mav.addObject("userRoles", userRoles);
        mav.addObject("userGenres", userGenres);
        mav.addObject("roleList", roleList);
        mav.addObject("genreList", genreList);

        return mav;
    }

    @RequestMapping(value = "/profile/edit", method = {RequestMethod.POST})
    public ModelAndView postEditProfile(@Valid @ModelAttribute("userEditForm") final UserEditForm userEditForm,
                                        final BindingResult errors) {
        if (errors.hasErrors()) {
            return editProfile(userEditForm);
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> optionalUser = userService.findByEmail(auth.getName());
        User user = optionalUser.orElseThrow(UserNotFoundException::new);

        userService.editUser(user.getId(), userEditForm.getName(), userEditForm.getSurname(), userEditForm.getDescription(),
                userEditForm.getMusicGenres(), userEditForm.getLookingFor(),
                userEditForm.getProfileImage().getBytes());

        return profile();

    }

    @RequestMapping(value = "/verify")
    public ModelAndView verify(@RequestParam(required = true) final String token) {
        userService.verifyUser(token);
        return new ModelAndView("views/verified");
    }

    @RequestMapping(value = "/resetPassword", method = {RequestMethod.GET})
    public ModelAndView resetPassword(@ModelAttribute("resetPasswordForm")
                                      final ResetPasswordForm resetPasswordForm) {

        ModelAndView mav = new ModelAndView("/views/resetPassword");
        mav.addObject("emailNotFound", false);
        return mav;
    }

    @RequestMapping(value = "/resetPassword", method = {RequestMethod.POST})
    public ModelAndView resetPassword(@Valid @ModelAttribute("resetPasswordForm")
                                      final ResetPasswordForm resetPasswordForm,
                                      final BindingResult errors) {
        if (errors.hasErrors()) {
           return resetPassword(resetPasswordForm);
        }
        userService.sendResetEmail(resetPasswordForm.getEmail());
        ModelAndView mav = new ModelAndView("/views/resetPassword");
        mav.addObject("resetPasswordForm",resetPasswordForm);
        mav.addObject("emailNotFound", false);
        return resetEmailSent(resetPasswordForm.getEmail());
    }

    @RequestMapping(value = "/newPassword", method = {RequestMethod.GET})
    public ModelAndView newPassword(@RequestParam(required = true) final String token,
                                    @ModelAttribute("newPasswordForm") final NewPasswordForm newPasswordForm) {
        if(verificationTokenService.isValid(token)) {
            ModelAndView mav = new ModelAndView("/views/newPassword");
            mav.addObject("token",token);
            return mav;
        }

        return new ModelAndView("redirect:/errors/404");
    }

    @RequestMapping(value = "/newPassword", method = {RequestMethod.POST})
    public ModelAndView newPassword(@RequestParam(required = true) final String token,
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
        ModelAndView emailSent = new ModelAndView("/views/resetEmailSent");
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
        ModelAndView emailSent = new ModelAndView("/views/emailSent");
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
        return new ModelAndView("views/login");
    }
}
