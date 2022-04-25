package ar.edu.itba.paw.service;
import ar.edu.itba.paw.model.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAll();

    List<Role> getRolesByAuditionId(long auditionId);

    List<Role> validateAndReturnRoles(List<String> rolesNames);

    List<Role> getUserRoles(long userId);
}