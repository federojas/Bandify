package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.form.ApplicationForm;
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
public class RegisterController {

    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
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
        return new ModelAndView("views/welcome");
    }

    @RequestMapping(value = "/register", method = {RequestMethod.GET})
    public ModelAndView registerView( @ModelAttribute("userForm") final UserForm userForm) {
        return new ModelAndView("views/register");
    }

}
