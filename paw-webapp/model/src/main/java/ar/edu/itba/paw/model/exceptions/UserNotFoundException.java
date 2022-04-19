package ar.edu.itba.paw.model.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("User not found with specified email.");
    }
}