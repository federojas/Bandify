package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.Role;
import ar.edu.itba.paw.model.exceptions.RoleNotFoundException;
import ar.edu.itba.paw.persistence.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Set<Role> getAll() {
        return roleDao.getAll();
    }

    @Override
    public Set<Role> getRolesByAuditionId(long auditionId) {
        return roleDao.getRolesByAuditionId(auditionId);
    }

    @Override
    public Set<Role> validateAndReturnRoles(List<String> rolesNames) {
        List<String> roles = roleDao.getAll().stream().map(Role::getName).collect(Collectors.toList());

        if(!roles.containsAll(rolesNames))
            throw new RoleNotFoundException();

        return roleDao.getRolesByNames(rolesNames);
    }

    @Override
    public Set<Role> getUserRoles(long userId) {
        return roleDao.getUserRoles(userId);
    }

    @Transactional
    @Override
    public void updateUserRoles(List<String> rolesNames, long userId) {
        if(rolesNames == null) {
            roleDao.updateUserRoles(null, userId);
            return;
        }
        Set<Role> newRoles = validateAndReturnRoles(rolesNames);
        roleDao.updateUserRoles(newRoles, userId);
    }


}
