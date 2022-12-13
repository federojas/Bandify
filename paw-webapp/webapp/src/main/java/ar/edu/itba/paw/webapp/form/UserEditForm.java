package ar.edu.itba.paw.webapp.form;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.*;

public class UserEditForm {

    @NotBlank
    @Size(max = 50)
    private String name;

    @Size(max = 50)
    private String surname;

    private boolean available;

    @Size(max = 500)
    private String description;

    @Size(max = 15)
    private List<String> musicGenres;

    @Size(max = 15)
    private List<String> lookingFor;

    @NotEmpty
    @Size(max = 100)
    private String location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public boolean getAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

}
