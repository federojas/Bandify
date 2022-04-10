package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.persistence.Audition;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

public class AuditionForm {

    @NotBlank
    @Size(min = 1, max = 100)
    private String title;

    @NotBlank
    @Size(min = 1, max = 512)
    private String description;

    @NotNull
    @Min(0)
    @Max(2147483647)
    private long location;

    @NotBlank
    @Email(regexp = "^\\w+([\\.-]?\\w+)*@\\w+([\\\\.-]?\\w+)*(\\.\\w{2,3})+$")
    private String email;

    @NotEmpty
    @Size(min = 1, max = 5)
    private List<Long> musicGenres;

    @NotEmpty
    @Size(min = 1, max = 5)
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
