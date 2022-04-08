package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Role;

import java.util.List;

public interface RoleDao {
    List<Role> getAll();
    List<Role> getRolesByAuditionId(long auditionId);

    void createAuditionRole(List<Role> roles, long auditionId);

    Role getRoleById(Long id);
}
