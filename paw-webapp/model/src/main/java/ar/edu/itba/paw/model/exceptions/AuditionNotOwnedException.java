package ar.edu.itba.paw.model.exceptions;

public class AuditionNotOwnedException extends RuntimeException {
    public AuditionNotOwnedException() {
        super("A specified audition is not owned by the current user");
    }
}