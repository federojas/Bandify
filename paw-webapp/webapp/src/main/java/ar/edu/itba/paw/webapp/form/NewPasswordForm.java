package ar.edu.itba.paw.webapp.form;
import javax.validation.constraints.Size;

public class NewPasswordForm {

    @Size(min = 8, max = 25)
    private String newPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
