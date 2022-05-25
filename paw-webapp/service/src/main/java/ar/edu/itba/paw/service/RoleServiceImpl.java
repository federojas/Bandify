package ar.edu.itba.paw.service;

import ar.edu.itba.paw.Role;
import ar.edu.itba.paw.model.exceptions.RoleNotFoundException;
import ar.edu.itba.paw.persistence.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public Set<Role> getAll() {
        return roleDao.getAll();
    }

    @Override
    public Set<Role> getRolesByNames(List<String> rolesNames) {
        if(rolesNames == null)
            throw new IllegalArgumentException();
        List<String> roles = roleDao.getAll().stream().map(Role::getName).collect(Collectors.toList());

        if(!roles.containsAll(rolesNames))
            throw new RoleNotFoundException();

        return roleDao.getRolesByNames(rolesNames);
    }

    //TODO ACA HAY QUE HACER ALGO
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
        Set<Role> newRoles = getRolesByNames(rolesNames);
        roleDao.updateUserRoles(newRoles, userId);
    }


}
