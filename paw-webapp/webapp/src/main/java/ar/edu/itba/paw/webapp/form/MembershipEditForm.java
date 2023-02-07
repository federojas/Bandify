package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.model.MembershipState;
import ar.edu.itba.paw.model.UserStatus;
import ar.edu.itba.paw.webapp.form.constraints.annotations.ValidEnum;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.util.List;

public class MembershipEditForm {

    @NotEmpty
    @Size(max = 5)
    private List<String> roles;

    @Size(max = 100)
    private String description;

    @NotEmpty
    @ValidEnum(enumClass = MembershipState.class)
    private String state;

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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
