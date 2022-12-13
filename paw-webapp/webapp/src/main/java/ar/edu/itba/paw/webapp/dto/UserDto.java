package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.User;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

// TODO: mostrar el mail solo en caso de que lo pida el usuario dueño
public class UserDto {

    private Long id;
    private String name;
    private String surname;
    private String description;
    private boolean isBand;
    private boolean isEnabled;
    private boolean available;
    private URI self;
    private URI location;
    private URI roles;
    private URI genres;
    private URI socialMedia;
    private URI applications;
    private URI profileImage;

    public static UserDto fromUser(final UriInfo uriInfo, final User user) {
        if(user == null)
            return null;
        UserDto dto = new UserDto();
        dto.id = user.getId();
        dto.name = user.getName();
        dto.surname = user.getSurname();
        dto.description = user.getDescription();
        dto.isBand = user.isBand();
        dto.isEnabled = user.isEnabled();
        dto.available = user.isAvailable();

        final UriBuilder userUriBuilder = uriInfo.getAbsolutePathBuilder()
                .replacePath("users").path(String.valueOf(user.getId()));
        dto.self = userUriBuilder.build();

        final UriBuilder locationUriBuilder = uriInfo.getAbsolutePathBuilder()
                .replacePath("locations");
        dto.location = locationUriBuilder.clone()
                .queryParam("user", String.valueOf(user.getId())).build();

        final UriBuilder roleUriBuilder = uriInfo.getAbsolutePathBuilder()
                .replacePath("roles");
        dto.roles = roleUriBuilder.clone()
                .queryParam("user", String.valueOf(user.getId())).build();

        final UriBuilder genreUriBuilder = uriInfo.getAbsolutePathBuilder()
                .replacePath("genres");
        dto.genres = genreUriBuilder.clone()
                .path("user")
                .path(String.valueOf(user.getId())).build();

        final UriBuilder socialMediaUriBuilder = uriInfo.getAbsolutePathBuilder()
                .replacePath("users").path(String.valueOf(user.getId()))
                .path("social-media");
        dto.socialMedia = socialMediaUriBuilder.build();

        final UriBuilder profileImageUriBuilder = uriInfo.getAbsolutePathBuilder()
                .replacePath("users").path(String.valueOf(user.getId()))
                .path("profile-image");
        dto.profileImage = profileImageUriBuilder.build();

        final UriBuilder applicationsUriBuilder = uriInfo.getAbsolutePathBuilder()
                .replacePath("users").path(String.valueOf(user.getId()))
                .path("applications");
        dto.applications = applicationsUriBuilder.build();

        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isBand() {
        return isBand;
    }

    public void setBand(boolean band) {
        isBand = band;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
    public URI getSelf() {
        return self;
    }

    public void setSelf(URI self) {
        this.self = self;
    }

    public URI getLocation() {
        return location;
    }

    public void setLocation(URI location) {
        this.location = location;
    }

    public URI getRoles() {
        return roles;
    }

    public void setRoles(URI roles) {
        this.roles = roles;
    }

    public URI getGenres() {
        return genres;
    }

    public void setGenres(URI genres) {
        this.genres = genres;
    }

    public URI getSocialMedia() {
        return socialMedia;
    }

    public void setSocialMedia(URI socialMedia) {
        this.socialMedia = socialMedia;
    }

    public URI getApplications() {
        return applications;
    }

    public void setApplications(URI applications) {
        this.applications = applications;
    }

    public URI getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(URI profileImage) {
        this.profileImage = profileImage;
    }
}
