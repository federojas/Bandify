package ar.edu.itba.paw.model.exceptions;

public class InvalidRoleException extends RuntimeException {

    public InvalidRoleException() {
        super("A specified role is invalid");
    }
}
