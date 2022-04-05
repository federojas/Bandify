package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Audition;
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
import java.sql.Date;
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
    public ModelAndView auditions(@ModelAttribute("auditionForm") final AuditionForm form) {
        final ModelAndView mav = new ModelAndView("home");
        /*
        Optional<Audition> audition = auditionService.getAuditionById(1);
        mav.addObject("audition", audition.get());
         */
        return mav;
    }

    @RequestMapping(value = "/create", method = {RequestMethod.POST})
    public ModelAndView create(@Valid @ModelAttribute("auditionForm") final AuditionForm form, final BindingResult errors) {
        if(errors.hasErrors())
            return auditions(form);

        final Audition audition = auditionService.create(form.getTitle(),form.getDescription(), form.getLocation(),
                                                         Date.valueOf("1999-04-04"), form.getMusicGenres(), form.getLookingFor());
        return new ModelAndView("redirect:/");
    }
    /*
    @RequestMapping("/")
    public ModelAndView index(@ModelAttribute("registerForm") final UserForm form) {
            return new ModelAndView("index");
}
    @RequestMapping(value = "/create", method = { RequestMethod.POST })
    public ModelAndView create(@Valid @ModelAttribute("registerForm") final UserForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            return index(form);


     */
}
