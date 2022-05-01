package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Role;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RoleDao {
    Set<Role> getAll();
    Set<Role> getRolesByAuditionId(long auditionId);

    void createAuditionRole(Set<Role> roles, long auditionId);

    Optional<Role> getRoleById(Long id);

    Optional<Role> getRoleByName(String name);

    Set<Role> getRolesByNames(List<String> rolesNames);

    Set<Role> getUserRoles(long userId);

    void updateUserRoles(Set<Role> newRoles, long userId);
}
