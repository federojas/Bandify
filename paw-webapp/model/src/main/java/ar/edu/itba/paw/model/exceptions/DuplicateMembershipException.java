package ar.edu.itba.paw.model.exceptions;

public class DuplicateMembershipException extends RuntimeException {
    public DuplicateMembershipException() {
        super("Membership already exists");
    }
}
