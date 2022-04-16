package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.webapp.form.AuditionForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @RequestMapping(value = "/", method = {RequestMethod.GET})
    public ModelAndView home(@ModelAttribute("auditionForm") final AuditionForm form) {
        return new ModelAndView("home");
    }

    @ExceptionHandler({LocationNotFoundException.class, GenreNotFoundException.class, RoleNotFoundException.class})
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ModelAndView badFormData() {
        return new ModelAndView("404");
    }

    @RequestMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/create", method = {RequestMethod.POST})
    public ModelAndView create(@Valid @ModelAttribute("auditionForm") final AuditionForm form,

                               final BindingResult errors) {
        if(errors.hasErrors())
            return home(form);

        auditionService.create(form.toBuilder(1).
                        location(locationService.getLocation(form.getLocation()).orElseThrow(LocationNotFoundException::new)).
                        lookingFor(roleService.validateAndReturnRoles(form.getLookingFor())).
                        musicGenres(genreService.validateAndReturnGenres(form.getMusicGenres()))
        );

        return new ModelAndView("redirect:/");
    }


    @RequestMapping(value = "/apply", method = {RequestMethod.POST})
    public ModelAndView apply(@Valid @ModelAttribute("applicationForm") final ApplicationForm form,
                              @RequestParam(required = false) final String auditionEmail,
                              final BindingResult errors) {
        if(errors.hasErrors())
            return home(new AuditionForm());

//        TODO: Este auditionEmail viene de una manera sin buen estilo de programación, hay que arreglarlo
//        input hidden
        try {
            mailingService.sendAuditionEmail(auditionEmail, form.getName(),
                    form.getEmail(),form.getMessage(), LocaleContextHolder.getLocale());
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return new ModelAndView("redirect:/");
    }
}
