package ar.edu.itba.paw.service;

import ar.edu.itba.paw.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {
    Set<Role> getAll();

    Set<Role> validateAndReturnRoles(List<String> rolesNames);

    Set<Role> getUserRoles(long userId);

    void updateUserRoles(List<String> rolesNames, long userId);
}