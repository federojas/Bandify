package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.Application;
import ar.edu.itba.paw.model.ApplicationState;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Objects;

public class ApplicationDto {
    private Long id;
    private ApplicationState state;
    private LocalDateTime creationDate;
    private String message;

    private URI self;
    private URI audition;
    private URI applicant;

    public static ApplicationDto fromApplication(final UriInfo uriInfo, final Application application) {
        if(application == null)
            return null;
        ApplicationDto applicationDto = new ApplicationDto();
        applicationDto.id = application.getId();
        applicationDto.state = application.getState();
        applicationDto.creationDate = application.getCreationDate();
        applicationDto.message = application.getMessage();

        final UriBuilder selfBuilder = uriInfo.getAbsolutePathBuilder()
                .replacePath("auditions").path(String.valueOf(application.getAudition().getId()))
                .replacePath("applications").path(String.valueOf(application.getId()));
        applicationDto.self = selfBuilder.build();

        final UriBuilder auditionUriBuilder = uriInfo.getAbsolutePathBuilder()
                .replacePath("auditions").path(String.valueOf(application.getAudition().getId()));
        applicationDto.audition = auditionUriBuilder.build();

        final UriBuilder applicantUriBuilder = uriInfo.getAbsolutePathBuilder()
                .replacePath("users").path(String.valueOf(application.getApplicant().getId()));
        applicationDto.applicant = applicantUriBuilder.build();
        return applicationDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ApplicationState getState() {
        return state;
    }

    public void setState(ApplicationState state) {
        this.state = state;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public URI getSelf() {
        return self;
    }

    public void setSelf(URI self) {
        this.self = self;
    }

    public URI getAudition() {
        return audition;
    }

    public void setAudition(URI audition) {
        this.audition = audition;
    }

    public URI getApplicant() {
        return applicant;
    }

    public void setApplicant(URI applicant) {
        this.applicant = applicant;
    }
}
