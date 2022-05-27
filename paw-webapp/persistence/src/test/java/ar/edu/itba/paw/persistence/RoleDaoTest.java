package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.Role;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:roleDaoTest.sql")
@Rollback
@Transactional
public class RoleDaoTest {
/*
    @Autowired
    private RoleJpaDao roleDao;
    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;

    private static final Role role = new Role(1,"role");
    private static final Role role2 = new Role(2,"role2");
    private static final Role role3 = new Role(3,"role3");
    private static final Role role4 = new Role(4,"role4");
    private static final Role role5 = new Role(5,"role5");

    private static final long USER_ID = 1;

    private static final long INVALID_ID = 20;
    private static final String INVALID_NAME = "INVALIDO";
    private static final List<String> ROLE_NAMES = Arrays.asList(role.getName(), role2.getName());
    private static final List<Role> ROLES = Arrays.asList(role, role2);
    private static final List<Role> USER_ROLES = Arrays.asList(role, role2);
    private static final List<Role> UPDATED_USER_ROLES = Arrays.asList(role2, role3);
    private static final List<Role> ALL_ROLES = Arrays.asList(role, role2, role3, role4, role5);

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void testGetRoleById() {
        final Optional<Role> optionalRole = roleDao.getRoleById(role.getId());
        assertNotNull(optionalRole);
        assertTrue(optionalRole.isPresent());
        assertEquals(role, optionalRole.get());
    }

    @Test
    public void testGetRoleByInvalidId() {
        final Optional<Role> optionalRole = roleDao.getRoleById(INVALID_ID);
        assertNotNull(optionalRole);
        assertFalse(optionalRole.isPresent());
    }

    @Test
    public void testGetRoleByName() {
        final Optional<Role> optionalRole = roleDao.getRoleByName(role.getName());
        assertNotNull(optionalRole);
        assertTrue(optionalRole.isPresent());
        assertEquals(role, optionalRole.get());
    }

    @Test
    public void testGetRoleByInvalidName() {
        final Optional<Role> optionalRole = roleDao.getRoleByName(INVALID_NAME);
        assertNotNull(optionalRole);
        assertFalse(optionalRole.isPresent());
    }

    @Test
    public void testGetAll() {
        final Set<Role> roleSet = roleDao.getAll();
        assertTrue(ALL_ROLES.containsAll(roleSet));
        assertEquals(JdbcTestUtils.countRowsInTable(jdbcTemplate, "roles"), roleSet.size());
    }

    @Test
    public void testGetRolesByNames() {
        final Set<Role> roleSet = roleDao.getRolesByNames(ROLE_NAMES);
        assertTrue(ROLES.containsAll(roleSet));
        assertEquals(JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "roles", "role = '" + ROLE_NAMES.get(0) + "' OR role = '" + ROLE_NAMES.get(1) + "'"), roleSet.size());
    }

    @Test
    public void testGetRolesByInvalidNames() {
        final Set<Role> roleSet = roleDao.getRolesByNames(Collections.singletonList(INVALID_NAME));
        assertTrue(roleSet.isEmpty());
    }

    //TODO ESTOS TESTS VUELAN?
//    @Test
//    public void testGetUserRoles() {
//        final Set<Role> roleSet = roleDao.getUserRoles(USER_ID);
//        assertTrue(USER_ROLES.containsAll(roleSet));
//        assertEquals(USER_ROLES.size(), roleSet.size());
//    }
//
//    @Test
//    public void testGetInvalidUserRoles() {
//        final Set<Role> roleSet = roleDao.getUserRoles(INVALID_ID);
//        assertTrue(roleSet.isEmpty());
//    }
//
//    @Test
//    public void testUpdateUserRoles() {
//        roleDao.updateUserRoles(new HashSet<>(UPDATED_USER_ROLES),USER_ID);
//        assertEquals(UPDATED_USER_ROLES.size(), JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "userroles", "userid = " + USER_ID));
//        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "userroles", "userid = " + USER_ID + " AND roleid = " + role.getId()));
//        assertEquals(UPDATED_USER_ROLES.size(), JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "userroles", "userid = " + USER_ID + " AND (roleid = " + role2.getId() + " OR roleid = " + role3.getId() + ")"));
//    }
//
//    @Test
//    public void testUpdateSameUserRoles() {
//        roleDao.updateUserRoles(new HashSet<>(USER_ROLES),USER_ID);
//        assertEquals(USER_ROLES.size(), JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "userroles", "userid = " + USER_ID));
//        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "userroles", "userid = " + USER_ID + " AND (roleid = " + role3.getId() + " OR roleid = " + role4.getId() + "OR roleid = " + role5.getId() + ")"));
//        assertEquals(USER_ROLES.size(), JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "userroles", "userid = " + USER_ID + " AND (roleid = " + role.getId() + " OR roleid = " + role2.getId() + ")"));
//    }

 */
}