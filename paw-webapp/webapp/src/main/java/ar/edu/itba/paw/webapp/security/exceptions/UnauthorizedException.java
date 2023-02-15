package ar.edu.itba.paw.webapp.security.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UnauthorizedException extends AuthenticationException {

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}