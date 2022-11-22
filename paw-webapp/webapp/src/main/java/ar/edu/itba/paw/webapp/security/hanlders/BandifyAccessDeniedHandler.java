package ar.edu.itba.paw.webapp.security.hanlders;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BandifyAccessDeniedHandler implements AccessDeniedHandler {

//TODO METEMOS UN LOGGER CON WARNING? VER ALTERNATIVAS

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/vnd.bandify.api.v1+json");
    }

}
