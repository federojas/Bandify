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
    private String timeElapsed;

    public static class AuditionBuilder {
        private String title, description, email;
        private LocalDateTime creationDate;
        private Location location;
        private List<Genre> musicGenres;
        private List<Role> lookingFor;
        private long bandId;
        private long id;
        private String timeElapsed;

        public AuditionBuilder(String title, String description, String email, long bandId, LocalDateTime creationDate) {
            this.creationDate = creationDate;
            this.title = title;
            this.description = description;
            this.email = email;
            this.bandId = bandId;
            this.timeElapsed = creationDate.toString();
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

    public String getTimeElapsed() {
        StringBuilder time = new StringBuilder("Hace ");
        LocalDateTime now = LocalDateTime.now();
        if(creationDate.plusSeconds(1).isAfter(now)) {
            time.append("menos de un segundo");
        } else if(creationDate.plusMinutes(1).isAfter(now)) {
            time.append(now.getSecond() - creationDate.getSecond()).append(" segundos");
        } else if(creationDate.plusHours(1).isAfter(now)) {
            time.append(now.getMinute() - creationDate.getMinute()).append(" minutos");
        } else if(creationDate.plusDays(1).isAfter(now)) {
            time.append(now.getHour() - creationDate.getHour()).append(" horas");
        } else if(creationDate.plusMonths(1).isAfter(now)) {
            time.append(now.getDayOfMonth() - creationDate.getDayOfMonth()).append(" dÃ\u00ADas");
        } else if(creationDate.plusYears(1).isAfter(now)) {
            time.append(now.getMonthValue() - creationDate.getMonthValue()).append(" meses");
        } else {
            time.append("más de un año");
        }
        return time.toString();
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
