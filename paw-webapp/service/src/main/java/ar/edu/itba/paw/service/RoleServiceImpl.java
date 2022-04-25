package ar.edu.itba.paw.service;
import ar.edu.itba.paw.model.Role;
import ar.edu.itba.paw.model.exceptions.RoleNotFoundException;
import ar.edu.itba.paw.persistence.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }
    @Override
    public List<Role> getAll() {
        return roleDao.getAll();
    }

    @Override
    public List<Role> getRolesByAuditionId(long auditionId) {
        return roleDao.getRolesByAuditionId(auditionId);
    }

    @Override
    public List<Role> validateAndReturnRoles(List<String> rolesNames) {
        List<Role> roles = roleDao.getRolesByNames(rolesNames);
        if(roles.size() != rolesNames.size())
            throw new RoleNotFoundException();
        return roles;
    }

    @Override
    public List<Role> getUserRoles(long userId) {
        return roleDao.getUserRoles(userId);
    }

    @Override
    public void addUserRoles(List<String> rolesNames, long userId) {
        roleDao.addUserRoles(rolesNames,userId);
    }


}
