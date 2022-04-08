package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.persistence.Audition;

import javax.validation.constraints.Size;
import java.util.List;

public class AuditionForm {

    // TODO: deberiamos validar los estilos musicales? usamos enum? consultar.
    @Size(min = 1, max = 100)
    private String title;

    @Size(min = 1, max = 512)
    private String description;

    private long location;

    @Size(min = 1, max = 100)
    private String email;

    @Size(min = 1, max = 5)
    private List<Long> musicGenres;

    @Size(min = 1, max = 5)
    private List<Long> lookingFor;

    public Audition.AuditionBuilder toBuilder(long bandId) {
        return new Audition.AuditionBuilder(title, description, email, bandId);
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
