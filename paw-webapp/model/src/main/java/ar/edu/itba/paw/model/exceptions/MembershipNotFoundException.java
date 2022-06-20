package ar.edu.itba.paw.model.exceptions;

public class MembershipNotFoundException extends RuntimeException {
    public MembershipNotFoundException() {
        super("A specified membership was not found");
    }
}