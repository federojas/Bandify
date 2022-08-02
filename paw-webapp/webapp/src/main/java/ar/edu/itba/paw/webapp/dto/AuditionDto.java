package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.Audition;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.LocalDateTime;

public class AuditionDto {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime creationDate;

    private URI self;
    private URI location;
    private URI lookingFor;
    private URI musicGenres;
    private URI applications;

    public static AuditionDto fromAudition(final UriInfo uriInfo, final Audition audition) {
        if(audition == null)
            return null;
        AuditionDto auditionDto = new AuditionDto();
        auditionDto.id = audition.getId();
        auditionDto.title = audition.getTitle();
        auditionDto.description = audition.getDescription();
        auditionDto.creationDate = audition.getCreationDate();

        final UriBuilder selfUriBuilder = uriInfo.getAbsolutePathBuilder()
                .replacePath("auditions").path(String.valueOf(audition.getId()));
        auditionDto.self = selfUriBuilder.build();

        final UriBuilder locationUriBuilder = uriInfo.getAbsolutePathBuilder()
                .replacePath("locations");
        auditionDto.location = locationUriBuilder.clone()
                .queryParam("audition", String.valueOf(audition.getId())).build();

        final UriBuilder rolesUriBuilder = uriInfo.getAbsolutePathBuilder()
                .replacePath("roles");
        auditionDto.lookingFor = rolesUriBuilder.clone()
                .queryParam("audition", String.valueOf(audition.getId())).build();

        final UriBuilder genresUriBuilder = uriInfo.getAbsolutePathBuilder()
                .replacePath("genres");
        auditionDto.musicGenres = genresUriBuilder.clone()
                .queryParam("audition", String.valueOf(audition.getId())).build();

        final UriBuilder applicationsUriBuilder = uriInfo.getAbsolutePathBuilder()
                .replacePath("auditions").path(String.valueOf(audition.getId()))
                .path("applications");
        auditionDto.applications = applicationsUriBuilder.clone().build();
        return auditionDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
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

    public URI getLookingFor() {
        return lookingFor;
    }

    public void setLookingFor(URI lookingFor) {
        this.lookingFor = lookingFor;
    }

    public URI getMusicGenres() {
        return musicGenres;
    }

    public void setMusicGenres(URI musicGenres) {
        this.musicGenres = musicGenres;
    }

    public URI getApplications() {
        return applications;
    }

    public void setApplications(URI applications) {
        this.applications = applications;
    }
}
