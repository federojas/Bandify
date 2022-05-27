package ar.edu.itba.paw;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_id_seq")
    @SequenceGenerator(sequenceName = "roles_id_seq",name="roles_id_seq")
    private long roleId;

    @Column(name = "role", length = 50, nullable = false, unique = true)
    private String roleName;

    Role() {
        //Hibernate
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
