package ar.edu.itba.paw.webapp.security.services;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response,
                                        final AuthenticationException exception) throws IOException, ServletException {
        setDefaultFailureUrl("/login?error=true");
        request.setAttribute("error", Boolean.TRUE);
        super.onAuthenticationFailure(request, response, exception);
        String key = "AbstractUserDetailsAuthenticationProvider.badCredentials";
        if (exception.getClass().equals(DisabledException.class)) {
            key = "AbstractUserDetailsAuthenticationProvider.disabled";
        }
        request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, key);
    }
}