package ar.edu.itba.paw.webapp.controller;
import ar.edu.itba.paw.webapp.form.AuditionForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WelcomeController {

    @RequestMapping(value = "/welcome", method = {RequestMethod.GET})
    public ModelAndView welcome() {
        return new ModelAndView("views/welcome");
    }

}