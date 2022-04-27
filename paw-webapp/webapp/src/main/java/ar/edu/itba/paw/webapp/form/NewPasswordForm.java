package ar.edu.itba.paw.webapp.form;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class NewPasswordForm {

    @Size(min = 8, max = 25)
    private String newPassword;

    @NotNull
    @Size(min = 8, max = 25)
    private String newPasswordConfirmation;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
        checkConfirmPassword();
    }

    public String getNewPasswordConfirmation() {
        return newPasswordConfirmation;
    }

    public void setNewPasswordConfirmation(String newPasswordConfirmation) {
        this.newPasswordConfirmation = newPasswordConfirmation;
        checkConfirmPassword();
    }

    private void checkConfirmPassword() {
        if(!this.newPassword.equals(newPasswordConfirmation)){
            this.newPasswordConfirmation = null;
        }
    }
}
