package ar.edu.itba.paw.webapp.form;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.util.List;

public class MembershipForm {

    @NotEmpty
    @Size(max = 5)
    private List<String> roles;

    @Size(max = 100)
    private String description;

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
