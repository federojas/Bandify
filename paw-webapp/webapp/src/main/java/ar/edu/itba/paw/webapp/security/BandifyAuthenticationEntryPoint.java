package ar.edu.itba.paw.webapp.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class BandifyAuthenticationEntryPoint implements AuthenticationEntryPoint {

    //TODO LOGGER ALTERNATIVAS?

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException exception) throws IOException {
        //TODO VER DE AGREGAR HEADERS AL RESPONSE COMO MEJORA
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/vnd.bandify.api.v1+json");
    }
}
