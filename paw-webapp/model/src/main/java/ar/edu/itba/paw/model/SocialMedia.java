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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @Column(nullable = false)
    private String url;

    @Enumerated(EnumType.STRING)
    private UrlType type;

    /* Default */ SocialMedia() {
        // Just for Hibernate
    }

    public SocialMedia(User user, String url, UrlType type) {
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SocialMedia that = (SocialMedia) o;
        return Objects.equals(user, that.user) && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, type);
    }
}
