package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Role;
import ar.edu.itba.paw.model.exceptions.InvalidRoleException;
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

    @Mock
    private RoleDao roleDao;

    @InjectMocks
    private RoleService roleService = new RoleServiceImpl();

    private static final Role GUITARIST = new Role(1, "Guitarist");
    private static final Role DRUMMER = new Role(2, "Drummer");
    private static final Role BASSIST = new Role(3, "Bassist");
    private static final Role SONGWRITER = new Role(4, "Songwriter");
    private static final Role COMPOSER = new Role(5, "Composer");
    private static final Role ARCHITECT = new Role(6, "Architect");
    private static final Role PRODUCER = new Role(7, "Producer");

    private static final List<Role> ROLE_LIST = Arrays.asList(GUITARIST, DRUMMER, BASSIST, SONGWRITER, COMPOSER, ARCHITECT, PRODUCER);
    private static final List<String> invalidRolesNames = new ArrayList<>(Collections.singletonList("InvalidRole"));

    @Test
    public void testGetAll() {
        when(roleDao.getAll()).thenReturn(new HashSet<>(ROLE_LIST));
        Assert.assertEquals(new HashSet<>(ROLE_LIST), roleService.getAll());
    }

    @Test
    public void testGetAllEmpty() {
        when(roleDao.getAll()).thenReturn(new HashSet<>());
        Assert.assertEquals(new HashSet<>(), roleService.getAll());
    }

    @Test
    public void testGetRolesByNames() {
        Set<Role> expected = new HashSet<>(Arrays.asList(GUITARIST, DRUMMER, BASSIST));
        when(roleDao.getAll()).thenReturn(new HashSet<>(ROLE_LIST));
        when(roleDao.getRolesByNames(Arrays.asList("Guitarist", "Drummer", "Bassist"))).thenReturn(expected);

        Set<Role> roles = roleService.getRolesByNames(Arrays.asList("Guitarist", "Drummer", "Bassist"));

        Assert.assertEquals(expected, roles);
    }

    @Test(expected = InvalidRoleException.class)
    public void testGetRolesByNamesWithInvalidRole() {
        when(roleDao.getAll()).thenReturn(new HashSet<>(ROLE_LIST));
        roleService.getRolesByNames(invalidRolesNames);
        Assert.fail("Should have thrown InvalidRoleException");
    }

    @Test
    public void testGetRolesByNamesWithNullNamesList() {
        Set<Role> roleSet = roleService.getRolesByNames(null);
        Assert.assertEquals(0,roleSet.size());
    }

    @Test
    public void testGetRolesByNamesWithEmptyNamesList() {
        Set<Role> roleSet = roleService.getRolesByNames(new ArrayList<>());
        Assert.assertEquals(0,roleSet.size());
    }

}
