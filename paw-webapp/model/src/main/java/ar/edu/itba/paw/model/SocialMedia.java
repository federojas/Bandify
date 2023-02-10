package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "profileMediaUrls")
public class SocialMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profilemediaurls_id_seq")
    @SequenceGenerator(sequenceName = "profilemediaurls_id_seq",name="profilemediaurls_id_seq")
    private Long id;

    @Column(nullable = false)
    private String url;

    @Enumerated(EnumType.STRING)
    private UrlType type;

    /* Default */ SocialMedia() {
        // Just for Hibernate
    }

    public SocialMedia(String url, UrlType type) {
        this.url = url;
        this.type = type;
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
        return type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}
