package ar.edu.itba.paw.service;

import ar.edu.itba.paw.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {
    Set<Role> getAll();

    Set<Role> getRolesByNames(List<String> rolesNames);
}