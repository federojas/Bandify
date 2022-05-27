package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Role;
import ar.edu.itba.paw.model.exceptions.RoleNotFoundException;
import ar.edu.itba.paw.persistence.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

}
