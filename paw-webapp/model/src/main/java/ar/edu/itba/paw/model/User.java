package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
@SecondaryTable(name = "profileimages", pkJoinColumns = @PrimaryKeyJoinColumn(name = "userId"))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
    @SequenceGenerator(name = "users_id_seq", sequenceName = "users_id_seq", allocationSize = 1)
    private Long id;

    @Column(length = 254, unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 50)
    private String surname;

    @Column(length = 500)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "locationId")
    private Location location;

    @Column
    private boolean isBand;

    @Column
    private boolean isEnabled;

    @Column
    private boolean available;

    @Column(table = "profileimages")
    @Basic(fetch = FetchType.LAZY)
    private byte[] image;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "userroles",
            joinColumns = @JoinColumn(name = "userid"),
            inverseJoinColumns = @JoinColumn(name = "roleid")
    )
    private Set<Role> userRoles;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "usergenres",
            joinColumns = @JoinColumn(name = "userid"),
            inverseJoinColumns = @JoinColumn(name = "genreid")
    )
    private Set<Genre> userGenres;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private Set<SocialMedia> socialSocialMedia;

    /* Default */ User() {
        // Just for Hibernate
    }

    public User(UserBuilder builder) {
        this.name = builder.name;
        this.surname = builder.surname;
        this.email = builder.email;
        this.password = builder.password;
        this.isBand = builder.isBand;
        this.isEnabled = builder.isEnabled;
        this.id = builder.id;
        this.description = builder.description;
        this.available = builder.available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return isBand == user.isBand &&
                isEnabled == user.isEnabled &&
                Objects.equals(id, user.id) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(name, user.name) &&
                Objects.equals(surname, user.surname) &&
                Objects.equals(description, user.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, name, surname, description, isBand, isEnabled);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public boolean isBand() {
        return isBand;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public String getDescription() {
        return description;
    }

    public byte[] getProfileImage() {
        return image;
    }

    public Set<Role> getUserRoles() {
        return userRoles;
    }

    public Set<Genre> getUserGenres() {
        return userGenres;
    }

    public Long getId() {
        return id;
    }

    public Set<SocialMedia> getSocialSocialMedia() {
        return socialSocialMedia;
    }

    public Location getLocation() {
        return location;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public void setProfileImage(byte[] profileImage) {
        this.image = profileImage;
    }

    public void setUserRoles(Set<Role> userRoles) {
        this.userRoles = userRoles;
    }

    public void setUserGenres(Set<Genre> userGenres) {
        this.userGenres = userGenres;
    }

    public void setSocialSocialMedia(Set<SocialMedia> socialSocialMedia) {
        this.socialSocialMedia = socialSocialMedia;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setAvailable(boolean available) {
            this.available = available;
    }

    public void editInfo(String name, String surname, String description) {
        setName(name);
        if(!this.isBand)
            setSurname(surname);
        setDescription(description);
    }

    public static class UserBuilder {
        private Long id;
        private String email, name;
        private String surname, description, password;
        private boolean isBand, isEnabled;
        private boolean available;

        /* Default */ UserBuilder() {
            // Just for Hibernate
        }

        public UserBuilder(String email, String password, String name, boolean isBand, boolean isEnabled) {
            this.email = email;
            this.password = password;
            this.name = name;
            this.isBand = isBand;
            this.isEnabled = isEnabled;
        }

        public UserBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder surname(String surname) {
            this.surname = surname;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder description(String description) {
            this.description = description;
            return this;
        }

        public User build() {
            if(this.name == null){
                throw new NullPointerException("The property 'name' is null.");
            }
            if(this.email == null){
                throw new NullPointerException("The property 'email' is null. ");
            }
            if(this.password == null){
                throw new NullPointerException("The property 'password' is null. ");
            }

            return new User(this);

        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

        public String getName() {
            return name;
        }

        public String getSurname() {
            return surname;
        }

        public boolean isBand() {
            return isBand;
        }

        public boolean isEnabled() {
            return isEnabled;
        }

        public String getDescription() {
            return description;
        }

        public Long getId() {
            return id;
        }

        public UserBuilder available(boolean available) {
            this.available = available;
            return this;
        }
    }
}
