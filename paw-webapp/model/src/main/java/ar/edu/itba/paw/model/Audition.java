package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "auditions")
public class Audition {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auditions_id_seq")
    @SequenceGenerator(name = "auditions_id_seq", sequenceName = "auditions_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bandId")
    private User band;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 300, nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "locationId")
    private Location location;

    @Column
    private boolean isOpen;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST})
    @JoinTable(name = "auditiongenres",
            joinColumns = @JoinColumn(name = "auditionId"),
            inverseJoinColumns = @JoinColumn(name = "genreId")
    )
    private Set<Genre> musicGenres;


    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST})
    @JoinTable(name = "auditionroles",
            joinColumns = @JoinColumn(name = "auditionId"),
            inverseJoinColumns = @JoinColumn(name = "roleId")
    )
    private Set<Role> lookingFor;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Audition audition = (Audition) o;
        return Objects.equals(getId(), audition.getId()) &&
                Objects.equals(getTitle(), audition.getTitle()) &&
                Objects.equals(getDescription(), audition.getDescription()) &&
                Objects.equals(getCreationDate(), audition.getCreationDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getDescription(), getCreationDate());
    }

    public void edit(AuditionBuilder builder) {
        title = builder.getTitle();
        description = builder.getDescription();
        musicGenres = builder.getMusicGenres();
        lookingFor = builder.getLookingFor();
        location = builder.getLocation();
    }

    public void setIsOpen(boolean open) {
        isOpen = open;
    }

    /* Default */ Audition() {
        // Just for Hibernate
    }

    public static class AuditionBuilder {
        private final String title, description;
        private final LocalDateTime creationDate;
        private Location location;
        private Set<Genre> musicGenres;
        private Set<Role> lookingFor;
        private final User band;
        private Long id;
        private boolean isOpen;

        public AuditionBuilder(String title, String description,User band, LocalDateTime creationDate) {
            this.creationDate = creationDate;
            this.title = title;
            this.description = description;
            this.band = band;
            this.lookingFor = new HashSet<>();
            this.musicGenres = new HashSet<>();
            this.isOpen = true;
        }

        public AuditionBuilder location(Location location) {
            this.location = location;
            return this;
        }

        public AuditionBuilder musicGenres(Set<Genre> musicGenres) {
            this.musicGenres = musicGenres;
            return this;
        }

        public AuditionBuilder lookingFor(Set<Role> lookingFor) {
            this.lookingFor = lookingFor;
            return this;
        }

        public AuditionBuilder lookingFor(Role role) {
            this.lookingFor.add(role);
            return this;
        }

        public AuditionBuilder musicGenres(Genre genre) {
            this.musicGenres.add(genre);
            return this;
        }

        public AuditionBuilder id(long id) {
            this.id = id;
            return this;
        }

        public Audition build() {
            return new Audition(this);
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public LocalDateTime getCreationDate() {
            return creationDate;
        }

        public User getBand() {
            return band;
        }

        public Location getLocation() {
            return location;
        }

        public Set<Genre> getMusicGenres() {
            return musicGenres;
        }

        public Set<Role> getLookingFor() {
            return lookingFor;
        }

        public Long getId() {
            return id;
        }

        public boolean isOpen() {
            return isOpen;
        }
    }

    private Audition(AuditionBuilder builder) {
        id = builder.id;
        band = builder.band;
        title = builder.title;
        description = builder.description;
        location = builder.location;
        creationDate = builder.creationDate;
        musicGenres = builder.musicGenres;
        lookingFor = builder.lookingFor;
        this.isOpen = true;
    }

    public Long getId() {
        return id;
    }

    public User getBand() {
        return band;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Location getLocation() {
        return location;
    }

    public Set<Genre> getMusicGenres() {
        return musicGenres;
    }

    public Set<Role> getLookingFor() {
        return lookingFor;
    }

    public boolean getIsOpen() {
        return isOpen;
    }
}
