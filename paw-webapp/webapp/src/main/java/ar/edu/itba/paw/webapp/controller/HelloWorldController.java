package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.model.Audition;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.AuditionService;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HelloWorldController {


    private final UserService us;
    private final AuditionService as;

    @Autowired
    public HelloWorldController(UserService us, AuditionService as) {
        this.us = us;
        this.as = as;
    }

    @RequestMapping("/helloWorld")
    public ModelAndView helloWorld(@RequestParam(name = "userId", defaultValue = "1") final long userId) {

        final ModelAndView mav = new ModelAndView("index");
        List<Audition> auditionList = as.getAll(1);
        mav.addObject("auditionList", auditionList);
        mav.addObject("user", us.getUserById(userId).orElseThrow(UserNotFoundException::new));
        return mav;
    }

    @RequestMapping("/helloWorld/create")
    public ModelAndView create(@RequestParam(value = "name", required = true) final String username,
                               @RequestParam(value = "password", required = true) final String password) {
        final User u = us.create(username, password);
        return new ModelAndView("redirect:/?userId=" + u.getId());
    }
}