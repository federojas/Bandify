package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.constraints.annotations.MaxFileSize;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.validation.constraints.Size;

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

    @MaxFileSize(8)
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
}
