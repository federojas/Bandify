package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "memberships")
public class Membership {

    // TODO: la sequence se genera con BIGINT, hay que cambiarlo en deploy y localmente tmb
    // TODO: EAGER VS LAZY

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "memberships_id_seq")
    @SequenceGenerator(name = "memberships_id_seq", sequenceName = "memberships_id_seq", allocationSize = 1)
    private Long id;

    /* Excluding CascadeType.REMOVE */
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST})
    @JoinColumn(name = "artistId")
    private User artist;

    /* Excluding CascadeType.REMOVE */
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST})
    @JoinColumn(name = "bandId")
    private User band;

    /* Excluding CascadeType.REMOVE */
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST})
    @JoinTable(name = "membershipRoles",
            joinColumns = @JoinColumn(name = "membershipId"),
            inverseJoinColumns = @JoinColumn(name = "roleId")
    )
    private Set<Role> roles;

    @Column(length = 100)
    private String description;

    @Enumerated(EnumType.STRING)
    private MembershipState state;

    /* Default */ Membership() {
        // Just for Hibernate
    }

    public Membership(Builder builder) {
        this.artist = builder.artist;
        this.band = builder.band;
        this.description = builder.description;
        this.roles = builder.roles;
        this.id = builder.id;
        this.state = builder.state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getArtist() {
        return artist;
    }

    public void setArtist(User artist) {
        this.artist = artist;
    }

    public User getBand() {
        return band;
    }

    public void setBand(User band) {
        this.band = band;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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

    public static class Builder {

        private Long id;
        private final User artist;
        private final User band;
        private final Set<Role> roles;
        private String description;
        private MembershipState state;

        public Builder(User artist, User band, Set<Role> roles) {
            this.artist = artist;
            this.band = band;
            this.roles = roles;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder state(MembershipState state) {
            this.state = state;
            return this;
        }

        public Membership build() {
            return new Membership(this);
        }

        public User getArtist() {
            return artist;
        }

        public User getBand() {
            return band;
        }
    }
}
