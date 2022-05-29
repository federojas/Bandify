package ar.edu.itba.paw.model;

import java.util.Objects;
import java.util.Set;

public class AuditionFilter {
    private Set<Genre> genres;
    private Set<Role> roles;
    private Set<Location> locations;
    private String title;
    private String order;

    public AuditionFilter(Set<Genre> genres, Set<Role> roles, Set<Location> locations, String title, String order) {
        this.genres = genres;
        this.roles = roles;
        this.locations = locations;
        this.title = title;
        this.order = order;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Location> getLocations() {
        return locations;
    }

    public void setLocations(Set<Location> locations) {
        this.locations = locations;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
