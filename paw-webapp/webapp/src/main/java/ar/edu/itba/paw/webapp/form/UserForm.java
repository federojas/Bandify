package ar.edu.itba.paw.webapp.form;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserForm {

    @Email
    @NotBlank
    private String email;

    @Size(min = 8, max = 25)
    private String password;

    @NotNull
    @Size(min = 8, max = 25)
    private String passwordConfirmation;

    @NotBlank
    @Size(max = 50)
    private String name;

    private boolean isBand;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        checkConfirmPassword();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBand() {
        return isBand;
    }

    public void setBand(boolean band) {
        isBand = band;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
        checkConfirmPassword();
    }

    private void checkConfirmPassword() {
        if(!this.password.equals(passwordConfirmation)){
            this.passwordConfirmation = null;
        }
    }
}
