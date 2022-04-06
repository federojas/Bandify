package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.model.Audition;
import ar.edu.itba.paw.service.AuditionService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.form.AuditionForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Controller
public class HomeController {
    private final AuditionService auditionService;
    private final UserService userService;

    @Autowired
    public HomeController(AuditionService auditionService, UserService userService) {
        this.auditionService = auditionService;
        this.userService = userService;
    }

    @RequestMapping(value = "/", method = {RequestMethod.GET})
    public ModelAndView home(@ModelAttribute("auditionForm") final AuditionForm form, @RequestParam(name = "userId", defaultValue = "1") final long userId) {
        final ModelAndView mav = new ModelAndView("home");
        /*
        Optional<Audition> audition = auditionService.getAuditionById(1);
        mav.addObject("audition", audition.get());
         */
        List<Audition> auditionList = auditionService.getAll(1);
        mav.addObject("auditionList", auditionList);
        mav.addObject("user", userService.getUserById(userId).orElseThrow(UserNotFoundException::new));
        return mav;
    }

    @RequestMapping(value = "/create", method = {RequestMethod.POST})
    public ModelAndView create(@Valid @ModelAttribute("auditionForm") final AuditionForm form, final BindingResult errors, @RequestParam(name = "userId", defaultValue = "1") final long userId) {
        if(errors.hasErrors())
            return home(form, userId);

        Date now = Date.valueOf(LocalDate.now());
        final Audition audition = auditionService.create(form.getTitle(),form.getDescription(), form.getLocation(),
               now , form.getMusicGenres(), form.getLookingFor());
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
