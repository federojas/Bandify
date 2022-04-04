package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Audition;
import ar.edu.itba.paw.service.AuditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class AuditionsController {
    private final AuditionService auditionService;

    @Autowired
    public AuditionsController(AuditionService auditionService) {
        this.auditionService = auditionService;
    }

    @RequestMapping(value = "/", method = {RequestMethod.GET})
    public ModelAndView auditions() {
        final ModelAndView mav = new ModelAndView("home");
        Optional<Audition> audition = auditionService.getAuditionById(1);
        mav.addObject("audition", audition.get());
        return mav;
    }

    @RequestMapping(value = "/", method = {RequestMethod.POST})
    public ModelAndView create() {

        return auditions();
    }

}
