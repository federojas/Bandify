package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.UserStatus;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class UserStatusDto {
    private UserStatus status;
    private URI self;
    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public URI getSelf() {
        return self;
    }

    public void setSelf(URI self) {
        this.self = self;
    }

    public static UserStatusDto fromUser(final UriInfo uriInfo, boolean status, long id) {
        UserStatusDto userStatusDto = new UserStatusDto();
        if(status)
            userStatusDto.status = UserStatus.VERIFIED;
        else
            userStatusDto.status = UserStatus.NOT_VERIFIED;

        final UriBuilder userUriBuilder = uriInfo.getAbsolutePathBuilder()
                .replacePath("users").path(String.valueOf(id)).path("status");
        userStatusDto.self = userUriBuilder.build();
        return userStatusDto;
    }

}
