package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.constraints.annotations.UserExists;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class SendTokenToEmailForm {

    @Email
    @NotBlank
    @UserExists
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
