package ar.edu.itba.paw.webapp.dto;


import ar.edu.itba.paw.model.Role;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Objects;

public class RoleDto {

    private long id;
    private String roleName;

    private URI self;

    public static RoleDto fromRole(final UriInfo uriInfo, final Role role) {
        if(role == null)
            return null;
        RoleDto dto = new RoleDto();
        dto.id = role.getId();
        dto.roleName = role.getName();

        final UriBuilder userUriBuilder = uriInfo.getBaseUriBuilder()
                .path("roles").path(String.valueOf(role.getId()));
        dto.self = userUriBuilder.build();

        return dto;
    }

    public URI getSelf() {
        return self;
    }

    public void setSelf(URI self) {
        this.self = self;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleDto roleDto = (RoleDto) o;
        return id == roleDto.id && Objects.equals(roleName, roleDto.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roleName);
    }
}
