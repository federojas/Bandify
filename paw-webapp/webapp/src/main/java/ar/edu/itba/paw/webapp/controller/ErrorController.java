package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.exceptions.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController {

    @ExceptionHandler(AuditionNotFoundException.class)
    public ModelAndView auditionNotFound() {
        return new ModelAndView("errors/404");
    }
//TODO estas excepciones van asi?
//    @ExceptionHandler(GenreNotFoundException.class)
//    public ModelAndView genreNotFound() {
//        return new ModelAndView("errors/404");
//    }
//
//    @ExceptionHandler(LocationNotFoundException.class)
//    public ModelAndView LocationNotFound() {
//        return new ModelAndView("errors/404");
//    }
//
//    @ExceptionHandler(RoleNotFoundException.class)
//    public ModelAndView RoleNotFound() {
//        return new ModelAndView("errors/404");
//    }
//
//    @ExceptionHandler(UserNotFoundException.class)
//    public ModelAndView UserNotFound() {
//        return new ModelAndView("errors/404");
//    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {
        int httpErrorCode = getErrorCode(httpRequest);

        switch (httpErrorCode) {
            case 400: {
                return new ModelAndView("errors/400");
            }
            case 401: {
                return new ModelAndView("errors/401");
            }
            case 403: {
                return new ModelAndView("errors/403");
            }
            case 404: {
                return new ModelAndView("errors/404");
            }
            case 500: {
                return new ModelAndView("errors/500");
            }
            default: {
                ModelAndView errorPage = new ModelAndView("errors/errorPage");
                String errorCode = String.valueOf(httpErrorCode);
                errorPage.addObject(errorCode);
                return errorPage;
            }
        }
    }

    @RequestMapping(value ="403")
    public ModelAndView accessDenied() {
        return new ModelAndView("errors/403");
    }

    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");
    }
}
