package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.persistence.Audition;
import ar.edu.itba.paw.service.AuditionService;
import ar.edu.itba.paw.webapp.form.AuditionForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class HomeController {
    private final AuditionService auditionService;

    @Autowired
    public HomeController(AuditionService auditionService) {
        this.auditionService = auditionService;
    }

    @RequestMapping(value = "/", method = {RequestMethod.GET})
    public ModelAndView home(@ModelAttribute("auditionForm") final AuditionForm form) {
        final ModelAndView mav = new ModelAndView("home");
        List<Audition> auditionList = auditionService.getAll(1);
        mav.addObject("auditionList", auditionList);
        return mav;
    }

    @RequestMapping(value = "/create", method = {RequestMethod.POST})
    public ModelAndView create(@Valid @ModelAttribute("auditionForm") final AuditionForm form, final BindingResult errors) {
        if(errors.hasErrors())
            return home(form);

        //TODO BANDSERVICE.GETID()
        auditionService.create(form.toBuilder(5));
        return new ModelAndView("redirect:/");
    }

}
