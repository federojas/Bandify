package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Role;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RoleDao {
    Set<Role> getAll();

    Optional<Role> getRoleById(Long id);

    Optional<Role> getRoleByName(String name);

    Set<Role> getRolesByNames(List<String> rolesNames);
}
