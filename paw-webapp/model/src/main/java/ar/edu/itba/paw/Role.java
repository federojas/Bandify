package ar.edu.itba.paw;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_roleid_seq")
    @SequenceGenerator(sequenceName = "roles_roleid_seq",name="roles_roleid_seq")
    private long roleId;

    @Column
    private String roleName;

    /* package */ Role() {
        // Just for Hibernate, we love you!
    }
    public Role(Builder builder) {
        this.roleId = builder.roleId;
        this.roleName = builder.roleName;
    }


    public static class Builder{
        private String roleName;
        private long roleId;

        public Builder(){
            //default
        }

        public Builder withRoleId(int roleId){
            this.roleId=roleId;
            return this;
        }
        public Builder withRoleName(String roleName){
            this.roleName=roleName;
            return this;
        }
        public Role build() {
            if (this.roleName == null) {
                throw new NullPointerException("The property \"roleName\" cannot be null. ");
            }
            return new Role(this);
        }
    }

    public long getId() {
        return roleId;
    }

    public String getName() {
        return roleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return getId() == role.getId() && Objects.equals(getName(), role.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, roleName);
    }
}
