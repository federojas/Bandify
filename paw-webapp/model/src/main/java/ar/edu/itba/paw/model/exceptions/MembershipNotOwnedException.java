package ar.edu.itba.paw.model.exceptions;

public class MembershipNotOwnedException extends RuntimeException {
    public MembershipNotOwnedException() {
        super("A specified membership is not owned by the current user");
    }

}
