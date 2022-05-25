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
    public Role(long roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
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
