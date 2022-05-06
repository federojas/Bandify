package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.exceptions.*;
import ar.edu.itba.paw.webapp.form.ResetPasswordForm;
import ar.edu.itba.paw.webapp.form.UserArtistForm;
import ar.edu.itba.paw.webapp.form.UserBandForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ErrorAdviceController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorController.class);

    @ExceptionHandler(NoPermissionsException.class)
    public ModelAndView noPermissions() {
        LOGGER.warn("No permissions for this action");
        return new ModelAndView("errors/403");
    }

    @ExceptionHandler(AuditionNotFoundException.class)
    public ModelAndView auditionNotFound() {
        LOGGER.warn("Audition could not be found");
        return new ModelAndView("errors/404");
    }

    @ExceptionHandler(AuditionNotOwnedException.class)
    public ModelAndView auditionNotOwned() {
        LOGGER.warn("Audition is not owned by current user");
        return new ModelAndView("errors/403");
    }


    @ExceptionHandler(GenreNotFoundException.class)
    public ModelAndView genreNotFound() {
        LOGGER.warn("Genre could not be found");
        return new ModelAndView("errors/404");
    }

    @ExceptionHandler(LocationNotFoundException.class)
    public ModelAndView LocationNotFound() {
        LOGGER.warn("Location could not be found");
        return new ModelAndView("errors/404");
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ModelAndView RoleNotFound() {
        LOGGER.warn("Role could not be found");
        return new ModelAndView("errors/404");
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ModelAndView UserNotFound() {
        // TODO: que muestre algo sobre que no se encontro el usuario
        LOGGER.warn("User could not be found");
        return new ModelAndView("errors/404");
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ModelAndView UserDuplicate(DuplicateUserException ex) {
        LOGGER.error("Duplicate email exception for user creation");
        ModelAndView mav = new ModelAndView("/views/register");
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
        mav.addObject("userEmailAlreadyExists", true);
        mav.addObject("isBand", isBand);
        return mav;
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ModelAndView EmailNotFound() {
        LOGGER.error("Email not found");
        ModelAndView mav = new ModelAndView("views/resetPassword");
        mav.addObject("emailNotFound", true);
        mav.addObject("resetPasswordForm", new ResetPasswordForm());
        return mav;
    }

}
