package ar.edu.itba.paw.service;

import ar.edu.itba.paw.Role;
import ar.edu.itba.paw.model.exceptions.RoleNotFoundException;
import ar.edu.itba.paw.persistence.RoleDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RoleServiceTest {
    /*
    @Mock
    private RoleDao roleDao;

    @InjectMocks
    private RoleService roleService = new RoleServiceImpl();

    private static final Role GUITARIST = new Role(1, "Guitarist");
    private static final Role DRUMMER = new Role(2, "Drummer");
    private static final List<Role> ROLE_LIST = Arrays.asList(GUITARIST, DRUMMER);
    private static final List<String> invalidRolesNames = new ArrayList<>(Collections.singletonList("InvalidRole"));

    @Test(expected = RoleNotFoundException.class)
    public void testGetRolesByNamesWithInvalidRole() {
        when(roleDao.getAll()).thenReturn(new HashSet<>(ROLE_LIST));
        roleService.getRolesByNames(invalidRolesNames);
        Assert.fail("Should have thrown RoleNotFoundException");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetRolesByNamesWithNullNamesList() {
        roleService.getRolesByNames(null);
        Assert.fail("Should have thrown IllegalArgumentException");
    }

    @Test
    public void testGetRolesByNamesWithEmptyNamesList() {
        when(roleDao.getAll()).thenReturn(new HashSet<>(ROLE_LIST));
        Set<Role> roleSet = roleService.getRolesByNames(new ArrayList<>());
        Assert.assertEquals(0,roleSet.size());
    }


     */

}
