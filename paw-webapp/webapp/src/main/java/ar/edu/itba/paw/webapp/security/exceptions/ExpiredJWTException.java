package ar.edu.itba.paw.webapp.security.exceptions;

public class ExpiredJWTException extends RuntimeException{
    public ExpiredJWTException(String message) {
        super(message);
    }
}