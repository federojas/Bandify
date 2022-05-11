package ar.edu.itba.paw.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {

    @RequestMapping(value = "/400")
    public ModelAndView error400() {
        return new ModelAndView("errors/400");
    }

    @RequestMapping(value = "/401")
    public ModelAndView error401() {
        return new ModelAndView("errors/401");
    }

    @RequestMapping(value = "/403")
    public ModelAndView error403() {
        return new ModelAndView("errors/403");
    }

    @RequestMapping(value = "/404")
    public ModelAndView error404() {
        return new ModelAndView("errors/404");
    }

    @RequestMapping(value = "/405")
    public ModelAndView error405() {
        return new ModelAndView("errors/405");
    }

    @RequestMapping(value = "/500")
    public ModelAndView error500() {
        return new ModelAndView("errors/500");
    }

}
