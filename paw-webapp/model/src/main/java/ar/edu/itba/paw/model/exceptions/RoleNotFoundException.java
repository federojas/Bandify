package ar.edu.itba.paw.model.exceptions;

public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException() {
        super("A specified musician role was not found");
    }
}

