package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Genre;
import ar.edu.itba.paw.model.Location;
import ar.edu.itba.paw.model.Role;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static java.time.Year.isLeap;
import static org.joda.time.DateTimeConstants.*;

public class Audition {
    private long id, bandId;
    private String title, description;
    private LocalDateTime creationDate;
    private Location location;
    private List<Genre> musicGenres;
    private List<Role> lookingFor;
    private String timeElapsed;

    public static class AuditionBuilder {
        private String title, description;
        private LocalDateTime creationDate;
        private Location location;
        private List<Genre> musicGenres;
        private List<Role> lookingFor;
        private long bandId;
        private long id;
        private String timeElapsed;

        public AuditionBuilder(String title, String description,long bandId, LocalDateTime creationDate) {
            this.creationDate = creationDate;
            this.title = title;
            this.description = description;
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
        creationDate = builder.creationDate;
        musicGenres = builder.musicGenres;
        lookingFor = builder.lookingFor;
    }

    public Audition(long id, long bandId, String title, String description, LocalDateTime creationDate, Location location, List<Genre> musicGenres, List<Role> lookingFor) {
        this.id = id;
        this.bandId = bandId;
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.location = location;
        this.musicGenres = musicGenres;
        this.lookingFor = lookingFor;
        this.timeElapsed = creationDate.toString();
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

    public String getTimeElapsed() {
        StringBuilder time = new StringBuilder("Hace ");
        LocalDateTime now = LocalDateTime.now();
        int toAppend;
        if(creationDate.plusSeconds(1).isAfter(now)) {
            time.append("menos de un segundo");
        } else if(creationDate.plusMinutes(1).isAfter(now)) {
            if(now.getSecond() < creationDate.getSecond())
                toAppend = SECONDS_PER_MINUTE - creationDate.getSecond() + now.getSecond();
            else
                toAppend = now.getSecond() - creationDate.getSecond();
            time.append(toAppend);
            if(toAppend == 1)
                time.append(" segundo");
            else
                time.append(" segundos");
        } else if(creationDate.plusHours(1).isAfter(now)) {
            if(now.getMinute() < creationDate.getMinute())
                toAppend = MINUTES_PER_HOUR - creationDate.getMinute() + now.getMinute();
            else
                toAppend = now.getMinute() - creationDate.getMinute();
            time.append(toAppend);
            if(toAppend == 1)
                time.append(" minuto");
            else
                time.append(" minutos");
        } else if(creationDate.plusDays(1).isAfter(now)) {
            if(now.getHour() < creationDate.getHour())
                toAppend = HOURS_PER_DAY - creationDate.getHour() + now.getHour();
            else
                toAppend= now.getHour() - creationDate.getHour();
            time.append(toAppend);
            if(toAppend == 1)
                time.append(" hora");
            else
                time.append(" horas");
        } else if(creationDate.plusMonths(1).isAfter(now)) {
            if(now.getDayOfMonth() < creationDate.getDayOfMonth())
                toAppend = creationDate.getMonth().length(isLeap(creationDate.getYear())) - creationDate.getDayOfMonth() + now.getDayOfMonth();
            else
                toAppend = now.getDayOfMonth() - creationDate.getDayOfMonth();
            time.append(toAppend);
            if(toAppend == 1)
                time.append(" día");
            else
                time.append(" días");
        } else if(creationDate.plusYears(1).isAfter(now)) {
            if(now.getMonthValue() < creationDate.getMonthValue())
                toAppend = Month.values().length - creationDate.getMonthValue() + now.getDayOfMonth();
            else
                toAppend = now.getDayOfMonth() - creationDate.getMonthValue();
            time.append(toAppend);
            if(toAppend == 1)
                time.append(" mes");
            else
                time.append(" meses");
        } else {
            time.append("mÃ¡s de un aÃ±o");
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
