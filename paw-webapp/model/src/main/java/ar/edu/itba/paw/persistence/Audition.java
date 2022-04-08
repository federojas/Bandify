package ar.edu.itba.paw.persistence;

import java.time.LocalDateTime;
import java.util.List;

public class Audition {
    private long id, bandId;
    private String title, description, location, email;
    private LocalDateTime creationDate;
    private List<String> musicGenres;
    private List<String> lookingFor;

    public static class AuditionBuilder {
        private String title, description, location, email;
        private LocalDateTime creationDate;
        private List<String> musicGenres;
        private List<String> lookingFor;
        private long bandId;

        public AuditionBuilder(String title, String description, String location, String email, List<String> musicGenres, List<String> lookingFor, long bandId) {
            this.creationDate = LocalDateTime.now();
            this.title = title;
            this.description = description;
            this.location = location;
            this.musicGenres = musicGenres;
            this.lookingFor = lookingFor;
            this.email = email;
            this.bandId = bandId;
        }

        protected Audition build(long id) {
            return new Audition(this, id);
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getLocation() {
            return location;
        }

        public String getEmail() {
            return email;
        }

        public LocalDateTime getCreationDate() {
            return creationDate;
        }

        public List<String> getMusicGenres() {
            return musicGenres;
        }

        public List<String> getLookingFor() {
            return lookingFor;
        }

        public long getBandId() {
            return bandId;
        }
    }

    private Audition(AuditionBuilder builder, long id) {
        this.id = id;
        bandId = builder.bandId;
        this.creationDate = builder.creationDate;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public List<String> getMusicGenres() {
        return musicGenres;
    }

    public void setMusicGenres(List<String> musicGenres) {
        this.musicGenres = musicGenres;
    }

    public List<String> getLookingFor() {
        return lookingFor;
    }

    public void setLookingFor(List<String> lookingFor) {
        this.lookingFor = lookingFor;
    }
}
