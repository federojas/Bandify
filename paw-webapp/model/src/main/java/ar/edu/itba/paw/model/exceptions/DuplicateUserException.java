package ar.edu.itba.paw.model.exceptions;

public class DuplicateUserException extends RuntimeException {

    private String name, surname;
    private boolean isBand;

    public DuplicateUserException(String name, String surname, boolean isBand) {
        this.name = name;
        this.surname = surname;
        this.isBand = isBand;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public boolean isBand() {
        return isBand;
    }
}
