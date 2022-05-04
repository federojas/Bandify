package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.exceptions.UserNotFoundException;
import ar.edu.itba.paw.persistence.User;
import ar.edu.itba.paw.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class UserInteractionController {

    private final UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserInteractionController.class);

    @Autowired
    public UserInteractionController(final UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/user/{id}", method = {RequestMethod.GET})
    public ModelAndView profile(@PathVariable long id) {

        Optional<User> optionalUser = userService.getUserById(id);
        User userToVisit = optionalUser.orElseThrow(UserNotFoundException::new);

        if(SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            optionalUser = userService.findByEmail(auth.getName());
            User user = optionalUser.orElseThrow(UserNotFoundException::new);

            if(user.getId() == userToVisit.getId())
                return new ModelAndView("redirect:/profile");
        }

        ModelAndView mav = new ModelAndView("views/viewProfile");
        mav.addObject("user", userToVisit);
        return mav;
    }

}
