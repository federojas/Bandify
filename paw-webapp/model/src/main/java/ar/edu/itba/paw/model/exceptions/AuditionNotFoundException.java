package ar.edu.itba.paw.model.exceptions;

public class AuditionNotFoundException extends RuntimeException {
    public AuditionNotFoundException() {
        super("A specified audition was not found");
    }
}
