package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.persistence.Audition;

import javax.validation.constraints.Size;

public class ApplicationForm {
    @Size(min = 1, max = 100)
    private String name;

    @Size(min = 1, max = 100)
    private String email;

    @Size(min = 1, max = 512)
    private String message;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
