package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.form.UserArtistForm;
import ar.edu.itba.paw.webapp.form.UserBandForm;
import ar.edu.itba.paw.webapp.security.services.AuthFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ar.edu.itba.paw.webapp.form.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class UserController {

    private final UserService userService;
    private final AuthFacade authFacade;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService, AuthFacade authFacade) {
        this.userService = userService;
        this.authFacade = authFacade;
    }

    @RequestMapping(value = "/register", method = {RequestMethod.GET})
    public ModelAndView registerView() {
        return new ModelAndView("views/register");
    }

    @RequestMapping(value = "/registerBand", method = {RequestMethod.GET})
    public ModelAndView registerBandView( @ModelAttribute("userBandForm") final UserBandForm userBandForm) {
        return new ModelAndView("views/registerBand");
    }

    @RequestMapping(value = "/registerBand", method = {RequestMethod.POST})
    public ModelAndView registerBand(@Valid @ModelAttribute("userBandForm") final UserBandForm userBandForm,
                                     final BindingResult errors) {
        if (errors.hasErrors()) {
            return registerBandView(userBandForm);
        }

        User.UserBuilder user = new User.UserBuilder(userBandForm.getEmail(), userBandForm.getPassword(),
                userBandForm.getName(), userBandForm.isBand());

        userService.create(user);
        LOGGER.debug("User with mail {} created", user.getEmail());

        return new WelcomeController().welcome();
    }

    @RequestMapping(value = "/registerArtist", method = {RequestMethod.GET})
    public ModelAndView registerArtistView( @ModelAttribute("userArtistForm") final UserArtistForm userArtistForm) {
        return new ModelAndView("views/registerArtist");
    }

    @RequestMapping(value = "/registerArtist", method = {RequestMethod.POST})
    public ModelAndView registerArtist(@Valid @ModelAttribute("userArtistForm") final UserArtistForm userArtistForm,
                                       final BindingResult errors) {
        if (errors.hasErrors()) {
            return registerArtistView(userArtistForm);
        }

        User.UserBuilder user = new User.UserBuilder(userArtistForm.getEmail(), userArtistForm.getPassword(),
                userArtistForm.getName(), userArtistForm.isBand())
                .surname(userArtistForm.getSurname());

        userService.create(user);
        LOGGER.debug("User with mail {} created", user.getEmail());

        return new WelcomeController().welcome();
    }

    @RequestMapping(value = "/profile", method = {RequestMethod.GET})
    public ModelAndView profile() {
        ModelAndView mav = new ModelAndView("views/profile");
        mav.addObject("user",authFacade.getCurrentUser());
        return mav;
    }

}
