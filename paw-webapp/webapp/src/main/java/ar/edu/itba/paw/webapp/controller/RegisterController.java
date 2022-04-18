package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegisterController {

    private final UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/register", method = {RequestMethod.POST})
    public ModelAndView register() {
        User.UserBuilder user = new User.UserBuilder("santi@mail.com","hola","santi",false,false);
        userService.create(user);
        LOGGER.debug("User with mail {} created", user.getEmail());
        return new ModelAndView("views/auditions");
    }

    @RequestMapping(value = "/register", method = {RequestMethod.GET})
    public ModelAndView registerView() {
        return new ModelAndView("views/register");
    }

}
