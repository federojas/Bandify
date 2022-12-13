package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Size;
import java.util.List;

public class UserGenresForm {
    @Size(max = 15)
    private List<String> musicGenres;

    public List<String> getMusicGenres() {
        return musicGenres;
    }

    public void setMusicGenres(List<String> musicGenres) {
        this.musicGenres = musicGenres;
    }
}
