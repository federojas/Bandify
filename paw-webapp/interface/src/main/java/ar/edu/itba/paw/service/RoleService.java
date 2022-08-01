package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Role;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RoleService {
    Set<Role> getAll();
    Optional<Role> getRoleById(Long id);
    Set<Role> getRolesByNames(List<String> rolesNames);
}