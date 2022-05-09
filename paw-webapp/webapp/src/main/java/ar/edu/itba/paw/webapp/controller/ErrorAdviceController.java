package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.exceptions.*;
import ar.edu.itba.paw.webapp.form.ResetPasswordForm;
import ar.edu.itba.paw.webapp.form.UserArtistForm;
import ar.edu.itba.paw.webapp.form.UserBandForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ErrorAdviceController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorController.class);

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AuditionNotFoundException.class)
    public ModelAndView auditionNotFound() {
        LOGGER.warn("Audition could not be found");
        return new ModelAndView("errors/404");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(GenreNotFoundException.class)
    public ModelAndView genreNotFound() {
        LOGGER.warn("Genre could not be found");
        return new ModelAndView("errors/404");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(LocationNotFoundException.class)
    public ModelAndView LocationNotFound() {
        LOGGER.warn("Location could not be found");
        return new ModelAndView("errors/404");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RoleNotFoundException.class)
    public ModelAndView RoleNotFound() {
        LOGGER.warn("Role could not be found");
        return new ModelAndView("errors/404");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ModelAndView UserNotFound() {
        // TODO: que muestre algo sobre que no se encontro el usuario
        LOGGER.warn("User could not be found");
        return new ModelAndView("errors/404");
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AuditionNotOwnedException.class)
    public ModelAndView auditionNotOwned() {
        LOGGER.warn("Audition is not owned by current user");
        return new ModelAndView("errors/403");
    }


    // TODO: ahora que hay un custom validator, que deberiamos devolver con estas ex?
    @ExceptionHandler(DuplicateUserException.class)
    public ModelAndView UserDuplicate(DuplicateUserException ex) {
        LOGGER.error("Duplicate email exception for user creation");
        ModelAndView mav = new ModelAndView("register");
        UserBandForm bandForm = new UserBandForm();
        UserArtistForm artistForm = new UserArtistForm();
        boolean isBand = ex.isBand();
        if(isBand) {
            bandForm.setName(ex.getName());
            bandForm.setBand(true);
        } else {
            artistForm.setSurname(ex.getSurname());
            artistForm.setName(ex.getName());
            artistForm.setBand(false);
        }
        mav.addObject("userArtistForm", artistForm);
        mav.addObject("userBandForm", bandForm);
        mav.addObject("isBand", isBand);
        return mav;
    }
}
