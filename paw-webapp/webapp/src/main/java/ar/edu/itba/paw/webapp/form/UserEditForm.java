package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.User;
import ar.edu.itba.paw.webapp.form.constraints.annotations.ImageType;
import ar.edu.itba.paw.webapp.form.constraints.annotations.MaxFileSize;
import org.hibernate.validator.constraints.NotBlank;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.validation.constraints.Size;
import java.util.List;

public abstract class UserEditForm {

    @NotBlank
    @Size(max = 50)
    private String name;

    @Size(max = 500)
    private String description;

    @Size(max = 15)
    private List<String> musicGenres;

    @Size(max = 15)
    private List<String> lookingFor;

    @MaxFileSize(8)
    @ImageType(types = {"image/png", "image/jpeg"})
    private CommonsMultipartFile profileImage;

    private boolean isBand;

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

    public boolean isBand() {
        return isBand;
    }

    public void setBand(boolean band) {
        isBand = band;
    }

    public void initialize(User user, List<String> musicGenres, List<String> bandRoles) {
        this.setMusicGenres(musicGenres);
        this.setLookingFor(bandRoles);
        this.setDescription(user.getDescription());
        this.setName(user.getName());
        this.setBand(user.isBand());
    }
}
