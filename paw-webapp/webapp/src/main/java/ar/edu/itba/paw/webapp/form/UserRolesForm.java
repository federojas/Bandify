package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Size;
import java.util.List;

public class UserRolesForm {
    @Size(max = 15)
    private List<String> roles;

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
