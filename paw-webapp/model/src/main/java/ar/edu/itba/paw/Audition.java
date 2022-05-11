package ar.edu.itba.paw;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Audition {
    private final long id, bandId;
    private final String title, description;
    private final LocalDateTime creationDate;
    private final Location location;
    private final Set<Genre> musicGenres;
    private final Set<Role> lookingFor;
    private final String bandName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Audition audition = (Audition) o;
        return getId() == audition.getId() && getBandId() == audition.getBandId() && getTitle().equals(audition.getTitle()) && getDescription().equals(audition.getDescription()) && getCreationDate().equals(audition.getCreationDate()) && getLocation().equals(audition.getLocation()) && getMusicGenres().equals(audition.getMusicGenres()) && getLookingFor().equals(audition.getLookingFor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getBandId(), getTitle(), getDescription(), getCreationDate(), getLocation(), getMusicGenres(), getLookingFor());
    }

    public static class AuditionBuilder {
        private final String title, description;
        private final LocalDateTime creationDate;
        private Location location;
        private Set<Genre> musicGenres;
        private Set<Role> lookingFor;
        private final long bandId;
        private long id;
        private String bandName;

        public AuditionBuilder(String title, String description,long bandId, LocalDateTime creationDate) {
            this.creationDate = creationDate;
            this.title = title;
            this.description = description;
            this.bandId = bandId;
            this.lookingFor = new HashSet<>();
            this.musicGenres = new HashSet<>();
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

        public AuditionBuilder bandName(String bandName) {
            this.bandName = bandName;
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

        public long getBandId() {
            return bandId;
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

        public long getId() {
            return id;
        }

        public String getBandName() {
            return bandName;
        }

    }

    private Audition(AuditionBuilder builder) {
        id = builder.id;
        bandId = builder.bandId;
        title = builder.title;
        description = builder.description;
        location = builder.location;
        creationDate = builder.creationDate;
        musicGenres = builder.musicGenres;
        lookingFor = builder.lookingFor;
        bandName = builder.bandName;
    }

    public long getId() {
        return id;
    }

    public long getBandId() {
        return bandId;
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

    public String getBandName() {
        return bandName;
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
}
