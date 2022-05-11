package ar.edu.itba.paw.model.exceptions;

public class DuplicateUserException extends RuntimeException {

    public DuplicateUserException() {
        super("User with specified email already exists");
    }

}
