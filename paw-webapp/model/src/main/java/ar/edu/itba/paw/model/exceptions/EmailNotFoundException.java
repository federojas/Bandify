package ar.edu.itba.paw.model.exceptions;

public class EmailNotFoundException extends UserNotFoundException {
    public EmailNotFoundException() {
        super("User not found with specified email");
    }
}
