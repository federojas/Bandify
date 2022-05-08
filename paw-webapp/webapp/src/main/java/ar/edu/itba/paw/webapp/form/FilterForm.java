package ar.edu.itba.paw.webapp.form;

import java.util.List;

public class FilterForm {

    private List<String> genresNames;

    private List<String> rolesNames;

    private String location;

    private String title;

    public List<String> getGenresNames() {
        return genresNames;
    }

    public void setGenresNames(List<String> genresNames) {
        this.genresNames = genresNames;
    }

    public List<String> getRolesNames() {
        return rolesNames;
    }

    public void setRolesNames(List<String> rolesNames) {
        this.rolesNames = rolesNames;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
