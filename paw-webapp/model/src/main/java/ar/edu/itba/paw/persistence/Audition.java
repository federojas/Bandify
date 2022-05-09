package ar.edu.itba.paw.persistence;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static java.time.Year.isLeap;
import static org.joda.time.DateTimeConstants.*;

public class Audition {
    private long id, bandId;
    private String title, description;
    private LocalDateTime creationDate;
    private Location location;
    private Set<Genre> musicGenres;
    private Set<Role> lookingFor;
    private String timeElapsed;
    private String bandName;

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
        private String title, description;
        private LocalDateTime creationDate;
        private Location location;
        private Set<Genre> musicGenres;
        private Set<Role> lookingFor;
        private long bandId;
        private long id;
        private String timeElapsed;
        private String bandName;

        public AuditionBuilder(String title, String description,long bandId, LocalDateTime creationDate) {
            this.creationDate = creationDate;
            this.title = title;
            this.description = description;
            this.bandId = bandId;
            this.timeElapsed = creationDate.toString();
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

    public Set<Genre> getMusicGenres() {
        return musicGenres;
    }

    public Set<Role> getLookingFor() {
        return lookingFor;
    }
}
