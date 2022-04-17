package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.webapp.form.AuditionForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @RequestMapping(value = "/", method = {RequestMethod.GET})
    public ModelAndView home(@ModelAttribute("auditionForm") final AuditionForm form) {
        return new ModelAndView("views/home");
    }

}