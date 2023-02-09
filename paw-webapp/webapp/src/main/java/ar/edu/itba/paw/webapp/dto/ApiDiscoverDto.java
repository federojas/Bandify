package ar.edu.itba.paw.webapp.dto;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class ApiDiscoverDto {
    private URI self;
    private URI auditionsUri;
    private URI membershipsUri;
    private URI genresUri;
    private URI usersUri;
    private URI rolesUri;
    private URI locationsUri;

    public static ApiDiscoverDto getApiDiscover(UriInfo uriInfo) {
        ApiDiscoverDto discoverDto = new ApiDiscoverDto();

        final UriBuilder selfUriBuilder = uriInfo.getAbsolutePathBuilder();
        discoverDto.self = selfUriBuilder.build();

        final UriBuilder auditionsUriBuilder = uriInfo.getAbsolutePathBuilder()
                .replacePath("auditions");
        discoverDto.auditionsUri = auditionsUriBuilder.build();

        final UriBuilder membershipsUriBuilder = uriInfo.getAbsolutePathBuilder()
                .replacePath("memberships");
        discoverDto.membershipsUri = membershipsUriBuilder.build();

        final UriBuilder genresUriBuilder = uriInfo.getAbsolutePathBuilder()
                .replacePath("genres");
        discoverDto.genresUri = genresUriBuilder.build();

        final UriBuilder rolesUriBuilder = uriInfo.getAbsolutePathBuilder()
                .replacePath("roles");
        discoverDto.rolesUri = rolesUriBuilder.build();

        final UriBuilder usersUriBuilder = uriInfo.getAbsolutePathBuilder()
                .replacePath("users");
        discoverDto.usersUri = usersUriBuilder.build();

        final UriBuilder locationsUriBuilder = uriInfo.getAbsolutePathBuilder()
                .replacePath("locations");
        discoverDto.locationsUri = locationsUriBuilder.build();

        return discoverDto;
    }

    public URI getSelf() {
        return self;
    }

    public void setSelf(URI self) {
        this.self = self;
    }

    public URI getAuditionsUri() {
        return auditionsUri;
    }

    public void setAuditionsUri(URI auditionsUri) {
        this.auditionsUri = auditionsUri;
    }

    public URI getMembershipsUri() {
        return membershipsUri;
    }

    public void setMembershipsUri(URI membershipsUri) {
        this.membershipsUri = membershipsUri;
    }

    public URI getGenresUri() {
        return genresUri;
    }

    public void setGenresUri(URI genresUri) {
        this.genresUri = genresUri;
    }

    public URI getUsersUri() {
        return usersUri;
    }

    public void setUsersUri(URI usersUri) {
        this.usersUri = usersUri;
    }

    public URI getRolesUri() {
        return rolesUri;
    }

    public void setRolesUri(URI rolesUri) {
        this.rolesUri = rolesUri;
    }

    public URI getLocationsUri() {
        return locationsUri;
    }

    public void setLocationsUri(URI locationsUri) {
        this.locationsUri = locationsUri;
    }
}
