package ar.edu.itba.paw;

import javax.persistence.*;
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

    @Column
    private boolean isBand;

    @Column
    private boolean isEnabled;

    @Column(table = "profileimages")
    @Basic(fetch = FetchType.LAZY)
    private byte[] profileImage;

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
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getId() == user.getId() && isBand() == user.isBand() && isEnabled() == user.isEnabled() && Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getPassword(), user.getPassword()) && Objects.equals(getName(), user.getName()) && Objects.equals(getSurname(), user.getSurname()) && Objects.equals(getDescription(), user.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), getPassword(), getName(), getSurname(), getDescription(), isBand(), isEnabled());
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
        return profileImage;
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
        this.profileImage = profileImage;
    }

    public void setUserRoles(Set<Role> userRoles) {
        this.userRoles = userRoles;
    }

    public void setUserGenres(Set<Genre> userGenres) {
        this.userGenres = userGenres;
    }

    public static class UserBuilder {
        private Long id;
        private String email, name;
        private String surname, description, password;
        private boolean isBand, isEnabled;

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
    }
}
