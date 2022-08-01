package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.Membership;
import ar.edu.itba.paw.model.MembershipState;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class MembershipDto {

    private Long id;
    private String description;
    private MembershipState state;

    private URI self;
    private URI roles;
    private URI artist;
    private URI band;

    public static MembershipDto fromMembership(final UriInfo uriInfo, final Membership membership) {
        if(membership == null)
            return null;

        MembershipDto dto = new MembershipDto();
        dto.id = membership.getId();
        dto.description = membership.getDescription();
        dto.state = membership.getState();

        final UriBuilder membershipUriBuilder = uriInfo.getAbsolutePathBuilder()
                .replacePath("memberships").path(String.valueOf(membership.getId()));
        dto.self = membershipUriBuilder.build();

        final UriBuilder roleUriBuilder = uriInfo.getAbsolutePathBuilder()
                .replacePath("roles");
        dto.roles = roleUriBuilder.queryParam("membership", String.valueOf(membership.getId())).build();

        final UriBuilder artistUriBuilder = uriInfo.getAbsolutePathBuilder()
                .replacePath("users").path(String.valueOf(membership.getArtist().getId()));
        dto.artist = artistUriBuilder.build();

        final UriBuilder bandUriBuilder = uriInfo.getAbsolutePathBuilder()
                .replacePath("users").path(String.valueOf(membership.getBand().getId()));
        dto.artist = bandUriBuilder.build();

        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MembershipState getState() {
        return state;
    }

    public void setState(MembershipState state) {
        this.state = state;
    }

    public URI getSelf() {
        return self;
    }

    public void setSelf(URI self) {
        this.self = self;
    }

    public URI getRoles() {
        return roles;
    }

    public void setRoles(URI roles) {
        this.roles = roles;
    }

    public URI getArtist() {
        return artist;
    }

    public void setArtist(URI artist) {
        this.artist = artist;
    }

    public URI getBand() {
        return band;
    }

    public void setBand(URI band) {
        this.band = band;
    }
}
