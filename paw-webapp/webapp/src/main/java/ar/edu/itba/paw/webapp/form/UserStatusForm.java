package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.model.UserStatus;
import ar.edu.itba.paw.webapp.form.constraints.annotations.ValidEnum;

public class UserStatusForm {
    @ValidEnum(enumClass = UserStatus.class)
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
