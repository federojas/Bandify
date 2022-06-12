package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ErrorAdviceController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorAdviceController.class);

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AuditionNotFoundException.class)
    public ModelAndView auditionNotFound() {
        LOGGER.warn("Audition could not be found");
        return new ModelAndView("forward:/404");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MembershipNotFoundException.class)
    public ModelAndView membershipNotFound() {
        LOGGER.warn("Membership could not be found");
        return new ModelAndView("forward:/404");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ApplicationNotFoundException.class)
    public ModelAndView applicationNotFound() {
        LOGGER.warn("Application could not be found");
        return new ModelAndView("forward:/404");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicateMembershipException.class)
    public ModelAndView duplicateMembership() {
        LOGGER.warn("Duplicated membership");
        return new ModelAndView("forward:/400");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ModelAndView illegalArgumentException() {
        LOGGER.warn("An illegal argument was found");
        return new ModelAndView("forward:/400");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(InvalidTokenException.class)
    public ModelAndView invalidToken() {
        LOGGER.warn("Specified token was expired or non existent");
        return new ModelAndView("forward:/404");
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(GenreNotFoundException.class)
    public ModelAndView genreNotFound() {
        LOGGER.warn("Genre could not be found");
        return new ModelAndView("errors/404");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(LocationNotFoundException.class)
    public ModelAndView locationNotFound() {
        LOGGER.warn("Location could not be found");
        return new ModelAndView("forward:/404");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RoleNotFoundException.class)
    public ModelAndView roleNotFound() {
        LOGGER.warn("Role could not be found");
        return new ModelAndView("forward:/404");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ModelAndView userNotFound() {
        LOGGER.warn("User could not be found");
        return new ModelAndView("forward:/404");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PageNotFoundException.class)
    public ModelAndView pageNotFound() {
        LOGGER.warn("Requested page was not found");
        return new ModelAndView("forward:/404");
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AuditionNotOwnedException.class)
    public ModelAndView auditionNotOwned() {
        LOGGER.warn("Audition is not owned by current user");
        return new ModelAndView("forward:/403");
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(BandNotOwnedException.class)
    public ModelAndView bandNotOwned() {
        LOGGER.warn("Band is not owned by current user");
        return new ModelAndView("forward:/403");
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(UserNotAvailableException.class)
    public ModelAndView userNotAvailable() {
        LOGGER.warn("User is not available");
        return new ModelAndView("forward:/403");
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(UserNotInBandException.class)
    public ModelAndView userNotInBAND() {
        LOGGER.warn("Current user is not in the specified band");
        return new ModelAndView("forward:/403");
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(MembershipNotOwnedException.class)
    public ModelAndView membershipNotOwned() {
        LOGGER.warn("Membership is not owned by current user");
        return new ModelAndView("forward:/403");
    }

}
