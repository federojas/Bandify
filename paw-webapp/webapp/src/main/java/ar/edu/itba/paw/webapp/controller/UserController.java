package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.UserService;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/register", method = {RequestMethod.POST})
    public ModelAndView register(@Valid @ModelAttribute("userForm") final UserForm userForm,
                                 final BindingResult errors) {

        if (errors.hasErrors()) {
            return registerView(userForm);
        }

        User.UserBuilder user = new User.UserBuilder(
                userForm.getEmail(), userForm.getPassword(),
                userForm.getName(), userForm.isBand(),
                userForm.isAdmin()
        ).surname(userForm.getSurname());

        userService.create(user);
        LOGGER.debug("User with mail {} created", user.getEmail());

        return new WelcomeController().welcome();
//        return new ModelAndView("views/auditions");
    }

    @RequestMapping(value = "/register", method = {RequestMethod.GET})
    public ModelAndView registerView( @ModelAttribute("userForm") final UserForm userForm) {
        return new ModelAndView("views/register");
    }

}
