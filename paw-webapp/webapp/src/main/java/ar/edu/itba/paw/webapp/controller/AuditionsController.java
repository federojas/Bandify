package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.persistence.Audition;
import ar.edu.itba.paw.service.AuditionService;
import ar.edu.itba.paw.webapp.form.ApplicationForm;
import ar.edu.itba.paw.webapp.form.AuditionForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class AuditionsController {

    private final AuditionService auditionService;

    @Autowired
    public AuditionsController(final AuditionService auditionService) {
        this.auditionService = auditionService;
    }

    @RequestMapping(value = "/auditions", method = {RequestMethod.GET})
    public ModelAndView auditions() {
        final ModelAndView mav = new ModelAndView("auditions");

        List<Audition> auditionList = auditionService.getAll(1);

        mav.addObject("auditionList", auditionList);

        return mav;
    }

    @RequestMapping(value = "/audition", method = {RequestMethod.GET})
    public ModelAndView audition(@ModelAttribute("applicationForm") final ApplicationForm applicationForm,
                                 @RequestParam(required = true) final long id
                                 ) {
        final ModelAndView mav = new ModelAndView("audition");

        Optional<Audition> audition = auditionService.getAuditionById(id);
        if (audition.isPresent()) {
            mav.addObject("audition", audition.get());
        } else {
//            ACA HAY QUE LLAMAR AL 404
            throw new IllegalStateException("Audition not found");
        }

        return mav;
    }

    @RequestMapping(value = "/audition", method = {RequestMethod.POST})
    public ModelAndView apply(@Valid @ModelAttribute("applicationForm") final ApplicationForm applicationForm,
                              @RequestParam(required = false) final String auditionEmail,
                              @RequestParam(required = false) final long id,
                              final BindingResult errors) {
        System.out.println(errors.hasErrors());
        if (errors.hasErrors()) {
            System.out.println(auditionEmail);
            System.out.println(id);
            return audition(applicationForm, id);
        }

        System.out.println(errors.hasErrors());
        System.out.println(auditionEmail);
        System.out.println(id);
//        MAILING SERVICE

//        System.out.println("no hubo error, mando mail al id " + id);
        return new ModelAndView("redirect:/audition?id=" + id);
    }

//    @RequestMapping(value = "/apply", method = {RequestMethod.POST})
//    public ModelAndView apply(@Valid @ModelAttribute("applicationForm") final ApplicationForm applicationForm,
//                              @RequestParam(required = false) final String auditionEmail,
//                              final BindingResult errors) {
//        if(errors.hasErrors())
//            return audition(applicationForm, 3);
//
////        TODO: Este auditionEmail viene de una manera sin buen estilo de programación, hay que arreglarlo
////        input hidden
//        try {
//            mailingService.sendAuditionEmail(auditionEmail, form.getName(),
//                    form.getEmail(),form.getMessage(), LocaleContextHolder.getLocale());
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//
//        return new ModelAndView("redirect:/");
//    }
}
