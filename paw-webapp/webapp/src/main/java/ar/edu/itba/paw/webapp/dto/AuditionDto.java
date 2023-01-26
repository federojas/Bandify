package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.Audition;
import ar.edu.itba.paw.model.Genre;
import ar.edu.itba.paw.model.Role;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class AuditionDto {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime creationDate;
    private URI self;
    private String location;
    private List<String> lookingFor;
    private List<String> musicGenres;
    private URI applications;
    private URI owner;

    public static AuditionDto fromAudition(final UriInfo uriInfo, final Audition audition) {
        if(audition == null)
            return null;
        AuditionDto auditionDto = new AuditionDto();
        auditionDto.id = audition.getId();
        auditionDto.title = audition.getTitle();
        auditionDto.description = audition.getDescription();
        auditionDto.creationDate = audition.getCreationDate();
        auditionDto.location = audition.getLocation().getName();
        auditionDto.musicGenres = audition.getMusicGenres().stream().map(Genre::getName).collect(Collectors.toList());
        auditionDto.lookingFor = audition.getLookingFor().stream().map(Role::getName).collect(Collectors.toList());

        final UriBuilder selfUriBuilder = uriInfo.getAbsolutePathBuilder()
                .replacePath("auditions").path(String.valueOf(audition.getId()));
        auditionDto.self = selfUriBuilder.build();

        final UriBuilder applicationsUriBuilder = uriInfo.getAbsolutePathBuilder()
                .replacePath("auditions").path(String.valueOf(audition.getId()))
                .path("applications");
        auditionDto.applications = applicationsUriBuilder.clone().build();

        final UriBuilder ownerUriBuilder = uriInfo.getAbsolutePathBuilder()
                .replacePath("users").path(String.valueOf(audition.getBand().getId()));
        auditionDto.owner = ownerUriBuilder.clone().build();
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getLookingFor() {
        return lookingFor;
    }

    public void setLookingFor(List<String> lookingFor) {
        this.lookingFor = lookingFor;
    }

    public List<String> getMusicGenres() {
        return musicGenres;
    }

    public void setMusicGenres(List<String> musicGenres) {
        this.musicGenres = musicGenres;
    }

    public URI getApplications() {
        return applications;
    }

    public void setApplications(URI applications) {
        this.applications = applications;
    }

    public URI getOwner() {
        return owner;
    }

    public void setOwner(URI owner) {
        this.owner = owner;
    }
}
