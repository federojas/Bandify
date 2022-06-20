package ar.edu.itba.paw.model.exceptions;

public class BandNotOwnedException extends RuntimeException {
    public BandNotOwnedException() {
        super("An action on a band not owned by the current user was attempted");
    }
}