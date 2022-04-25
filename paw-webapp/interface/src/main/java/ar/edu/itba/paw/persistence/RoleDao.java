package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleDao {
    List<Role> getAll();
    List<Role> getRolesByAuditionId(long auditionId);

    void createAuditionRole(List<Role> roles, long auditionId);

    Optional<Role> getRoleById(Long id);

    Optional<Role> getRoleByName(String name);

    List<Role> getRolesByNames(List<String> rolesNames);

    List<Role> getUserRoles(long userId);

    void addUserRoles(List<String> rolesNames, long userId);
}
