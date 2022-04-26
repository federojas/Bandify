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

    @NotEmpty
    private String location;

    @NotEmpty
    @Size(max = 5)
    private List<String> musicGenres;

    @NotEmpty
    @Size(max = 5)
    private List<String> lookingFor;

    public Audition.AuditionBuilder toBuilder(long bandId, String email) {
        return new Audition.AuditionBuilder(title, description, email, bandId, LocalDateTime.now());
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }


    public void setTitle(String title) {
        this.title = title;
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
