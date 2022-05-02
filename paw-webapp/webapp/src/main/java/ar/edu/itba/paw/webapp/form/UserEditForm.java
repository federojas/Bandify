package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.constraints.annotations.ImageType;
import ar.edu.itba.paw.webapp.form.constraints.annotations.MaxFileSize;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.List;

public class UserEditForm {

    @NotBlank
    @Size(max = 50)
    private String name;

    @NotBlank
    @Size(max = 50)
    private String surname;

    @NotBlank
    @Size(max = 500)
    private String description;

    @NotEmpty
    @Size(max = 5)
    private List<String> musicGenres;

    @NotEmpty
    @Size(max = 5)
    private List<String> lookingFor;

    @MaxFileSize(8) //mb
    @ImageType(types = {"image/png", "image/jpeg"})
    private CommonsMultipartFile profileImage;

    public CommonsMultipartFile getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(CommonsMultipartFile profileImage) {
        this.profileImage = profileImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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
}
