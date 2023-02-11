package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.SocialMedia;
import ar.edu.itba.paw.model.UrlType;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class SocialMediaDto {
    private Long id;
    private String url;
    private UrlType mediaType;

    private URI self;

    public static SocialMediaDto fromSocialMedia(final long userId, final UriInfo uriInfo, SocialMedia socialMedia) {
        SocialMediaDto socialMediaDto = new SocialMediaDto();
        socialMediaDto.id = socialMedia.getId();
        socialMediaDto.url = socialMedia.getUrl();
        socialMediaDto.mediaType = socialMedia.getType();

        final UriBuilder selfUriBuilder = uriInfo.getBaseUriBuilder()
                .replacePath("users").path(String.valueOf(userId))
                .path("social-media").path(String.valueOf(socialMedia.getId()));
        socialMediaDto.self = selfUriBuilder.build();

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
        return mediaType;
    }

    public void setType(UrlType type) {
        this.mediaType = type;
    }

    public URI getSelf() {
        return self;
    }

    public void setSelf(URI self) {
        this.self = self;
    }

}
