package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "profileMediaUrls")
public class SocialMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profileMediaUrls_id_seq")
    @SequenceGenerator(sequenceName = "profileMediaUrls_id_seq",name="profileMediaUrls_id_seq")
    private Long id;

    @Column(nullable = false)
    private String url;

    @Enumerated(EnumType.STRING)
    private UrlType mediaType;

    /* Default */ SocialMedia() {
        // Just for Hibernate
    }

    public SocialMedia(String url, UrlType mediaType) {
        this.url = url;
        this.mediaType = mediaType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public UrlType getMediaType() {
        return mediaType;
    }

    public void setMediaType(UrlType mediaType) {
        this.mediaType = mediaType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SocialMedia that = (SocialMedia) o;
        return mediaType == that.mediaType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mediaType);
    }
}
