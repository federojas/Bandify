package ar.edu.itba.paw.webapp.form;
import javax.validation.constraints.Size;

public class NewPasswordForm {

    @Size(min = 8, max = 25)
    private String newPassword;

    @Size(min = 8, max = 25)
    private String newPasswordConfirmation;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordConfirmation() {
        return newPasswordConfirmation;
    }

    public void setNewPasswordConfirmation(String newPasswordConfirmation) {
        this.newPasswordConfirmation = newPasswordConfirmation;
    }
}
