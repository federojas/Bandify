package ar.edu.itba.paw.model.exceptions;

public class AuditionGoneException extends RuntimeException {
    public AuditionGoneException() {
        super("A specified audition is closed and is no longer viewable");
    }
}
