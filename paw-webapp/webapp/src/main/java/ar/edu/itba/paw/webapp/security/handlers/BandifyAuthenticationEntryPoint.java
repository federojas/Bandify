package ar.edu.itba.paw.webapp.security.handlers;

import ar.edu.itba.paw.webapp.dto.ErrorInfoDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class BandifyAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException exception) throws IOException {
        ErrorInfoDto errorInfoDto = new ErrorInfoDto();


        //TODO VER DE AGREGAR LOS HEADERS DE WWW-AUTHENTICATE
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/vnd.bandify.api.v1+json");

        errorInfoDto.setStatus(HttpStatus.UNAUTHORIZED.value());
        errorInfoDto.setTitle(HttpStatus.UNAUTHORIZED.getReasonPhrase());
        errorInfoDto.addMessage(exception.getMessage());
        errorInfoDto.setPath(request.getRequestURI());

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getWriter(), errorInfoDto);
    }
}
