package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.SocialMedia;
import ar.edu.itba.paw.model.UrlType;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class SocialMediaDto {
    private Long id;
    private String url;
    private UrlType type;

    private URI self;
    private URI user;

    public static SocialMediaDto fromSocialMedia(final UriInfo uriInfo, SocialMedia socialMedia) {
        SocialMediaDto socialMediaDto = new SocialMediaDto();
        socialMediaDto.id = socialMedia.getId();
        socialMediaDto.url = socialMedia.getUrl();
        socialMediaDto.type = socialMedia.getType();

        final UriBuilder selfUriBuilder = uriInfo.getAbsolutePathBuilder()
                .replacePath("users").path(String.valueOf(socialMedia.getUser().getId()))
                .path("social-media").path(String.valueOf(socialMedia.getId()));
        socialMediaDto.self = selfUriBuilder.build();

        final UriBuilder userUriBuilder = uriInfo.getAbsolutePathBuilder()
                .replacePath("users").path(String.valueOf(socialMedia.getUser().getId()));
        socialMediaDto.user = userUriBuilder.clone().build();
        return socialMediaDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public UrlType getType() {
        return type;
    }

    public void setType(UrlType type) {
        this.type = type;
    }

    public URI getSelf() {
        return self;
    }

    public void setSelf(URI self) {
        this.self = self;
    }

    public URI getUser() {
        return user;
    }

    public void setUser(URI user) {
        this.user = user;
    }
}
