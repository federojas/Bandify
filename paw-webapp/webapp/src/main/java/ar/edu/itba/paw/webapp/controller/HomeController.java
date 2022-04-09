package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Genre;
import ar.edu.itba.paw.model.Location;
import ar.edu.itba.paw.model.Role;
import ar.edu.itba.paw.persistence.Audition;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.form.ApplicationForm;
import ar.edu.itba.paw.webapp.form.AuditionForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@Controller
public class HomeController {
    private final AuditionService auditionService;
    private final RoleService roleService;
    private final GenreService genreService;
    private final LocationService locationService;
    private final MailingService mailingService;

    @Autowired
    public HomeController(AuditionService auditionService, RoleService roleService, GenreService genreService, LocationService locationService, MailingService mailingService) {
        this.auditionService = auditionService;
        this.roleService = roleService;
        this.genreService = genreService;
        this.locationService = locationService;
        this.mailingService = mailingService;
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("applicationForm", new ApplicationForm());
    }

    @RequestMapping(value = "/", method = {RequestMethod.GET})
    public ModelAndView home(@ModelAttribute("auditionForm") final AuditionForm form) {
        final ModelAndView mav = new ModelAndView("home");

        List<Audition> auditionList = auditionService.getAll(1);
        List<Role> roleList = roleService.getAll();
        List<Genre> genreList = genreService.getAll();
        List<Location> locationList = locationService.getAll();

        mav.addObject("auditionList", auditionList);
        mav.addObject("roleList", roleList);
        mav.addObject("genreList", genreList);
        mav.addObject("locationList", locationList);

//        try {
//            mailingService.sendAuditionEmail("g8hcicuenta1@gmail.com","franco","asdad@gmail","jpña cp,p aidns", LocaleContextHolder.getLocale());
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }

        return mav;
    }

    @RequestMapping(value = "/create", method = {RequestMethod.POST})
    public ModelAndView create(@Valid @ModelAttribute("auditionForm") final AuditionForm form, final BindingResult errors) {
        if(errors.hasErrors())
            return home(form);

        auditionService.create(form.toBuilder(1).
                        location(locationService.validateAndGetLocation(form.getLocation())).
                        lookingFor(roleService.validateAndReturnRoles(form.getLookingFor())).
                        musicGenres(genreService.validateAndReturnGenres(form.getMusicGenres()))
        );
        return new ModelAndView("redirect:/");
    }


    @RequestMapping(value = "/apply", method = {RequestMethod.POST})
    public ModelAndView apply(@Valid @ModelAttribute("applicationForm") final ApplicationForm form, final BindingResult errors) {
        System.out.println(form.getEmail());
        System.out.println(form.getName());
        System.out.println(form.getPhone());
        System.out.println(form.getSurname());

        return new ModelAndView("redirect:/");
    }
}
