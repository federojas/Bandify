package ar.edu.itba.paw.webapp.security.handlers;

import ar.edu.itba.paw.webapp.dto.ErrorInfoDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Component
public class BandifyAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException exception) throws IOException {
        ErrorInfoDto errorInfoDto = new ErrorInfoDto();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON);
        response.addHeader("WWW-Authenticate", "Basic realm=\"User Visible Realm\", charset=\"UTF-8\"");
        response.addHeader("WWW-Authenticate", "Bearer realm=\"token\"");
        response.addHeader("WWW-Authenticate", "Basic realm=\"email:nonce\"");
        errorInfoDto.setStatus(HttpStatus.UNAUTHORIZED.value());
        errorInfoDto.setTitle(HttpStatus.UNAUTHORIZED.getReasonPhrase());
        errorInfoDto.addMessage(exception.getMessage());
        errorInfoDto.setPath(request.getRequestURI());

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getWriter(), errorInfoDto);
    }
}
