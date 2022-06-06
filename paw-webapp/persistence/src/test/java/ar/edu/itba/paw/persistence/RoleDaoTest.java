package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Role;
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

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;

    private static final Role role1 = new Role(1L, "role");
    private static final Role role2 = new Role(2L, "role2");
    private static final Role role3 = new Role(3L, "role3");
    private static final Role role4 = new Role(4L, "role4");
    private static final Role role5 = new Role(5L, "role5");


    private static final long INVALID_ID = 20;
    private static final String INVALID_NAME = "INVALIDO";
    private static final List<String> ROLE_NAMES = Arrays.asList(role1.getName(), role2.getName());
    private static final List<Role> ROLES_BY_NAMES = Arrays.asList(role1, role2);
    private static final List<Role> ROLES = Arrays.asList(role1, role2, role3, role4, role5);

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void testGetRoleById() {
        final Optional<Role> optionalRole = roleDao.getRoleById(role1.getId());

        assertNotNull(optionalRole);
        assertTrue(optionalRole.isPresent());

        assertEquals(role1, optionalRole.get());
    }

    @Test
    public void testGetRoleByInvalidId() {
        final Optional<Role> optionalRole = roleDao.getRoleById(INVALID_ID);
        assertNotNull(optionalRole);
        assertFalse(optionalRole.isPresent());
    }

    @Test
    public void testGetRoleByName() {
        final Optional<Role> optionalRole = roleDao.getRoleByName(role2.getName());

        assertNotNull(optionalRole);
        assertTrue(optionalRole.isPresent());

        assertEquals(role2, optionalRole.get());
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

        assertNotNull(roleSet);
        assertEquals(5, roleSet.size());

        assertTrue(ROLES.containsAll(roleSet));
        assertEquals(ROLES.size(), roleSet.size());
        assertEquals(JdbcTestUtils.countRowsInTable(jdbcTemplate, "roles"), roleSet.size());
    }

    @Test
    public void testGetRolesByNames() {
        final Set<Role> roleSet = roleDao.getRolesByNames(ROLE_NAMES);
        assertNotNull(roleSet);
        assertEquals(2, roleSet.size());
        assertTrue(ROLES_BY_NAMES.containsAll(roleSet));
        assertEquals(ROLES_BY_NAMES.size(), roleSet.size());
        assertEquals(JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "roles", "role = '" + ROLE_NAMES.get(0) + "' OR role = '" + ROLE_NAMES.get(1) + "'"), roleSet.size());
    }

    @Test
    public void testGetRolesByInvalidNames() {
        final Set<Role> roleSet = roleDao.getRolesByNames(Collections.singletonList(INVALID_NAME));
        assertNotNull(roleSet);
        assertTrue(roleSet.isEmpty());
    }

}