package ar.edu.itba.paw.webapp.dto;


import ar.edu.itba.paw.model.Role;

import javax.ws.rs.core.UriInfo;

public class RoleDto {

    private long id;
    private String roleName;

    public static RoleDto fromRole(final UriInfo uriInfo, final Role role) {
        if(role == null)
            return null;
        RoleDto dto = new RoleDto();
        dto.id = role.getId();
        dto.roleName = role.getName();

        return dto;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
