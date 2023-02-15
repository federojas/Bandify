package ar.edu.itba.paw.webapp.security.exceptions;

public class BandifyBadRequestException extends RuntimeException{
    public BandifyBadRequestException(String message) {
        super(message);
    }
}
