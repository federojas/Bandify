package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.form.UserArtistForm;
import ar.edu.itba.paw.webapp.form.UserBandForm;
import ar.edu.itba.paw.webapp.security.services.SecurityFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final SecurityFacade securityFacade;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService, SecurityFacade securityFacade) {
        this.userService = userService;
        this.securityFacade = securityFacade;
    }

    @RequestMapping(value = "/register", method = {RequestMethod.GET})
    public ModelAndView registerView(@ModelAttribute("userBandForm") final UserBandForm userBandForm,
                                     @ModelAttribute("userArtistForm") final UserArtistForm userArtistForm) {
        return new ModelAndView("views/register");
    }

    @RequestMapping(value = "/registerBand", method = {RequestMethod.POST})
    public ModelAndView registerBand(@Valid @ModelAttribute("userBandForm") final UserBandForm userBandForm,
                                     final BindingResult errors) {
        if (errors.hasErrors()) {
            ModelAndView returnRegister = registerView(userBandForm, new UserArtistForm());
            returnRegister.addObject("userArtistForm", new UserArtistForm());
            return returnRegister;
        }

        User.UserBuilder user = new User.UserBuilder(userBandForm.getEmail(), userBandForm.getPassword(),
                userBandForm.getName(), userBandForm.isBand());

        userService.create(user);
        LOGGER.debug("User with mail {} created", user.getEmail());

        return new WelcomeController().welcome();
    }

    @RequestMapping(value = "/registerArtist", method = {RequestMethod.POST})
    public ModelAndView registerArtist(@Valid @ModelAttribute("userArtistForm") final UserArtistForm userArtistForm,
                                       final BindingResult errors) {
        if (errors.hasErrors()) {
            ModelAndView returnRegister = registerView(new UserBandForm(), userArtistForm);
            returnRegister.addObject("userBandForm", new UserBandForm());
            return returnRegister;
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
        mav.addObject("user", securityFacade.getCurrentUser());
        return mav;
    }

}
