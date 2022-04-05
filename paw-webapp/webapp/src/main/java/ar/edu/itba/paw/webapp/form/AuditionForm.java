package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

public class AuditionForm {

    // TODO: deberiamos validar los estilos musicales? usamos enum? consultar.
    @Size(min = 1, max = 100)
    private String title;

    @Size(min = 1, max = 512)
    private String description;

    @Size(min = 1, max = 100)
    private String location;

    @Size(min = 1, max = 5)
    private List<String> musicGenres;

    @Size(min = 1, max = 5)
    private List<String> lookingFor;

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
