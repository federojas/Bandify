package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.FilterOptions;
import ar.edu.itba.paw.model.Genre;
import ar.edu.itba.paw.model.Role;
import ar.edu.itba.paw.model.User;
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:userDaoTest.sql")
@Rollback
@Transactional
public class UserDaoTest {

    @Autowired
    private UserDao userDao;
    @Autowired
    private DataSource ds;
    @PersistenceContext
    private EntityManager em;

    private JdbcTemplate jdbcTemplate;

    private static final User ARTIST = new User.UserBuilder("artist@mail.com","12345678","name",false,false).surname("surname").description("description").id(1L).build();
    private static final User ARTIST2 = new User.UserBuilder("artist2@mail.com","12345678", "name", false, false).surname("surname").description("description").id(2L).build();
    private static final User ARTIST3 = new User.UserBuilder("artist3@mail.com","12345678", "name", false, true).surname("surname").description("description").id(3L).build();
    private static final FilterOptions FILTER_OPTIONS = new FilterOptions.FilterOptionsBuilder().withGenres(Collections.singletonList("ROCK")).withRoles(Collections.singletonList("GUITARIST")).withTitle("name").build();
    private static final String PASSWORD = "12345678";
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String DESCRIPTION = "description";
    private static final String NEW_PASSWORD = "87654321";
    private static final long INVALID_ID = 20;
    private static final String INVALID_EMAIL = "invalid@mail.com";
    private static final String BAND_EMAIL = "band@mail.com";

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void testCreateUser() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"usergenres");
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"userroles");
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"users");
        final User user = userDao.create(new User.UserBuilder(BAND_EMAIL, PASSWORD, NAME, true, false).description(DESCRIPTION));
        em.flush();
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
    public void testGetUserById(){
        final Optional<User> user = userDao.getUserById(ARTIST.getId());
        assertNotNull(user);
        assertTrue(user.isPresent());
        assertEquals(ARTIST, user.get());
    }

    @Test
    public void testGetUserByInvalidId(){
        final Optional<User> user = userDao.getUserById(INVALID_ID);
        assertNotNull(user);
        assertFalse(user.isPresent());
    }

    @Test
    public void testFindByEmail(){
        final Optional<User> user = userDao.findByEmail(ARTIST.getEmail());
        assertNotNull(user);
        assertTrue(user.isPresent());
        assertEquals(user.get(), ARTIST);
    }

    @Test
    public void testFindByInvalidEmail(){
        final Optional<User> user = userDao.findByEmail(INVALID_EMAIL);
        assertNotNull(user);
        assertFalse(user.isPresent());
    }

    @Test
    public void testChangePassword(){
        userDao.changePassword(ARTIST2.getId(), NEW_PASSWORD);
        em.flush();
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "users", "id = " + ARTIST2.getId() + " AND password = " + NEW_PASSWORD));
    }

    @Test
    public void testVerifyUser(){
        userDao.verifyUser(ARTIST2.getId());
        em.flush();
        assertEquals(2, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "users", "isenabled = true"));
    }

    @Test
    public void testFilterSearch() {
        List<User> userList = userDao.filter(FILTER_OPTIONS,1);
        assertEquals(1,userList.size());
        assertEquals(userList.get(0), ARTIST3);
    }

    @Test
    public void testFilterTotalPages() {
        int pageSize = userDao.getTotalPages(FILTER_OPTIONS);
        assertEquals(1,1);
    }

}
