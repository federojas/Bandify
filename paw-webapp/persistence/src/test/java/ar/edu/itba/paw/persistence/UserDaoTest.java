package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.User;
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
import java.util.Optional;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:userDaoTest.sql")
@Rollback
@Transactional
public class UserDaoTest {

    @Autowired
    private UserJdbcDao userDao;
    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;

    private static final User artist = new User.UserBuilder("artist@mail.com","12345678", "name", false, false).surname("surname").description("description").id(1).build();
    private static final User artist2 = new User.UserBuilder("artist2@mail.com","12345678", "name", false, false).surname("surname").description("description").id(2).build();

    private static final String PASSWORD = "12345678";
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String DESCRIPTION = "description";

    private static final String NEW_PASSWORD = "87654321";
    private static final String NEW_NAME = "newname";
    private static final String NEW_SURNAME = "newsurname";
    private static final String NEW_DESCRIPTION = "newdescription";

    private static final long INVALID_ID = 20;
    private static final String INVALID_EMAIL = "invalid@mail.com";

    private static final String BAND_EMAIL = "band@mail.com";

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void testCreateUser() {
        JdbcTestUtils.deleteFromTableWhere(jdbcTemplate,"users", "id <> 1");
        final User user = userDao.create(new User.UserBuilder(BAND_EMAIL, PASSWORD, NAME, true, false).description(DESCRIPTION));
        assertNotNull(user);
        assertEquals(NAME, user.getName());
        assertNull(SURNAME, user.getSurname());
        assertEquals(BAND_EMAIL, user.getEmail());
        assertEquals(PASSWORD, user.getPassword());
        assertEquals(DESCRIPTION, user.getDescription());
        assertTrue(user.isBand());
        assertFalse(user.isEnabled());
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "users", "id = " + user.getId()));
    }


    @Test
    public void testEditUser() {
        userDao.editUser(2, NEW_NAME, NEW_SURNAME, NEW_DESCRIPTION);
        String query = "name = '" + NEW_NAME + "' AND surname = '" + NEW_SURNAME + "' AND description = '" + NEW_DESCRIPTION + "'";
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "users", query));
    }

    @Test
    public void testGetUserById(){
        final Optional<User> user = userDao.getUserById(artist.getId());
        assertNotNull(user);
        assertTrue(user.isPresent());
        assertEquals(artist, user.get());
    }

    @Test
    public void testGetUserByInvalidId(){
        final Optional<User> user = userDao.getUserById(INVALID_ID);
        assertNotNull(user);
        assertFalse(user.isPresent());
    }

    @Test
    public void testFindByEmail(){
        final Optional<User> user = userDao.findByEmail(artist.getEmail());
        assertNotNull(user);
        assertTrue(user.isPresent());
        assertEquals(user.get(), artist);
    }

    @Test
    public void testFindByInvalidEmail(){
        final Optional<User> user = userDao.findByEmail(INVALID_EMAIL);
        assertNotNull(user);
        assertFalse(user.isPresent());
    }

    @Test
    public void testChangePassword(){
        userDao.changePassword(artist2.getId(), NEW_PASSWORD);
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "users", "id = " + artist2.getId() + " AND password = " + NEW_PASSWORD));
    }

    @Test
    public void testVerifyUser(){
        userDao.verifyUser(artist2.getId());
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "users", "isenabled = true"));
    }

}
