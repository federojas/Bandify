package ar.edu.itba.paw.webapp.security.filters;

import antlr.StringUtils;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exceptions.UserNotFoundException;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

// TODO: chequear si está bien que esto sea un Component
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final UserService userService;

    @Autowired
    public JwtFilter(final UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String receivedHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        if(receivedHeader == null || receivedHeader.isEmpty() ||
                !(receivedHeader.startsWith("Bearer") || receivedHeader.startsWith("Basic"))) {
            filterChain.doFilter(httpServletRequest,httpServletResponse);
        }

        final String payload = receivedHeader.split(" ")[1].trim();
        Authentication auth;
        if(receivedHeader.startsWith("Basic")){
            String[] decodedCredentials;
            decodedCredentials = new String(Base64.getDecoder().decode(payload), StandardCharsets.UTF_8).split(":");
            if (decodedCredentials.length != 2) {
                throw new AuthenticationCredentialsNotFoundException("Invalid credentials for basic authorization.");
            }
            final String email = decodedCredentials[0];
            final String password = decodedCredentials[1];

            // TODO: obtener auth manager
            /*
            auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

             */

            //final User user = userService.findByEmail(auth.getName()).orElseThrow(UserNotFoundException::new);

            // TODO: agregar tokens
            //httpServletResponse.addHeader("X-JWT", "untoken");
            //httpServletResponse.addHeader("X-Refresh-Token", "unrefreshtoken");
        } else {
            // TODO: startsWith Bearer
        }
    }
}
