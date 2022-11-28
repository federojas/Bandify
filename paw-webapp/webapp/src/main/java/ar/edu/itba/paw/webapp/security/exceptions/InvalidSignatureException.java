package ar.edu.itba.paw.webapp.security.exceptions;

public class InvalidSignatureException extends RuntimeException{
    public InvalidSignatureException(String message) {
        super(message);
    }
}
