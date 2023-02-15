package ar.edu.itba.paw.model.exceptions;

public class UserAlreadyAppliedToAuditionException extends RuntimeException {
    public UserAlreadyAppliedToAuditionException() {
        super("User already applied to this audition");
    }
}
