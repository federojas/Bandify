package ar.edu.itba.paw.model;

public class User {
    private long id;
    private String email, password, name, surname;
    private boolean isBand, isEnabled;

    private User(UserBuilder builder) {
        this.name = builder.name;
        this.surname = builder.surname;
        this.email = builder.email;
        this.password = builder.password;
        this.isBand = builder.isBand;
        this.isEnabled = builder.isEnabled;
        this.id = builder.id;
    }

    public long getId() {
        return id;
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

    public static class UserBuilder {
        private long id;
        private String email, password, name, surname;
        private boolean isBand, isEnabled;

        public UserBuilder(String email, String password, String name, boolean isBand, boolean isEnabled) {
            this.email = email;
            this.password = password;
            this.name = name;
            this.isBand = isBand;
            this.isEnabled = isEnabled;
        }

        public UserBuilder id(long id) {
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

        public long getId() {
            return id;
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

        public void setEnabled(boolean enabled) {
            isEnabled = enabled;
        }
    }
}
