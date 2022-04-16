package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.persistence.Audition;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

public class AuditionForm {

    @NotBlank
    @Size(max = 50)
    private String title;

    @NotBlank
    @Size(max = 300)
    private String description;

    @Max(value = 2147483647)
    @Min(value = 1)
    private long location;

    @Email
    @NotBlank
    private String email;

    @NotEmpty
    @Size(max = 5)
    private List<Long> musicGenres;

    @NotEmpty
    @Size(max = 5)
    private List<Long> lookingFor;

    public Audition.AuditionBuilder toBuilder(long bandId) {
        return new Audition.AuditionBuilder(title, description, email, bandId, LocalDateTime.now());
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

    public long getLocation() {
        return location;
    }

    public List<Long> getMusicGenres() {
        return musicGenres;
    }

    public List<Long> getLookingFor() {
        return lookingFor;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(long location) {
        this.location = location;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMusicGenres(List<Long> musicGenres) {
        this.musicGenres = musicGenres;
    }

    public void setLookingFor(List<Long> lookingFor) {
        this.lookingFor = lookingFor;
    }
}
