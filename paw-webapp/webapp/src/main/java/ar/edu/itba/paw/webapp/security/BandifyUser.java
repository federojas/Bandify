package ar.edu.itba.paw.webapp.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Objects;

public class BandifyUser extends User {
    private long id;
    private String email, name, password, surname;
    private boolean isBand;

    public BandifyUser(String username, String password, Collection<? extends GrantedAuthority> authorities,
                       long id, String name, String surname, boolean isBand) {
        super(username, password, authorities);
        this.id = id;
        this.password = password;
        this.email = username;
        this.name = name;
        this.surname = surname;
        this.isBand = isBand;
    }

    public ar.edu.itba.paw.model.User toUser() {
        return new ar.edu.itba.paw.model.User.UserBuilder(
                email,password,name,isBand
        ).id(id).surname(surname).build();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public boolean isBand() {
        return isBand;
    }

    public void setBand(boolean band) {
        isBand = band;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BandifyUser that = (BandifyUser) o;
        return id == that.id ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
