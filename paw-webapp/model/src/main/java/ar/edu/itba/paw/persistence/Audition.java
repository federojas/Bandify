package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Genre;
import ar.edu.itba.paw.model.Location;
import ar.edu.itba.paw.model.Role;

import java.time.LocalDateTime;
import java.util.List;

public class Audition {
    private long id, bandId;
    private String title, description, email;
    private LocalDateTime creationDate;
    private Location location;
    private List<Genre> musicGenres;
    private List<Role> lookingFor;

    public static class AuditionBuilder {
        private String title, description, email;
        private LocalDateTime creationDate;
        private Location location;
        private List<Genre> musicGenres;
        private List<Role> lookingFor;
        private long bandId;
        private long id;

        public AuditionBuilder(String title, String description, String email, long bandId) {
            this.creationDate = LocalDateTime.now();
            this.title = title;
            this.description = description;
            this.email = email;
            this.bandId = bandId;
        }

        public AuditionBuilder location(Location location) {
            this.location = location;
            return this;
        }

        public AuditionBuilder musicGenres(List<Genre> musicGenres) {
            this.musicGenres = musicGenres;
            return this;
        }

        public AuditionBuilder lookingFor(List<Role> lookingFor) {
            this.lookingFor = lookingFor;
            return this;
        }

        public AuditionBuilder id(long id) {
            this.id = id;
            return this;
        }

        protected Audition build() {
            return new Audition(this);
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getEmail() {
            return email;
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

        public List<Genre> getMusicGenres() {
            return musicGenres;
        }

        public List<Role> getLookingFor() {
            return lookingFor;
        }

        public long getId() {
            return id;
        }
    }

    private Audition(AuditionBuilder builder) {
        id = builder.id;
        bandId = builder.bandId;
        title = builder.title;
        description = builder.description;
        location = builder.location;
        email = builder.email;
        creationDate = builder.creationDate;
        musicGenres = builder.musicGenres;
        lookingFor = builder.lookingFor;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBandId() {
        return bandId;
    }

    public void setBandId(long bandId) {
        this.bandId = bandId;
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

    public Location getLocation() {
        return location;
    }

    public List<Genre> getMusicGenres() {
        return musicGenres;
    }

    public List<Role> getLookingFor() {
        return lookingFor;
    }
}
