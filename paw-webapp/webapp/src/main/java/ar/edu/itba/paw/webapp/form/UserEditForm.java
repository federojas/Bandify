package ar.edu.itba.paw.webapp.form;

import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserEditForm {
    @NotBlank
    @Size(max = 50)
    private String name;

    @Size(max = 50)
    private String surname;

    private boolean available;

    @Size(max = 500)
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public boolean getAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

}
