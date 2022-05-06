package ar.edu.itba.paw.persistence;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
@Rollback
@Transactional
public class UserDaoTest extends TestPopulator {

    @Autowired
    private UserJdbcDao userDao;

    private final static RowMapper<User.UserBuilder> USER_ROW_MAPPER = (rs, rowNum) -> new User.UserBuilder(rs.getString("email"),
            rs.getString("password"),
            rs.getString("name"),
            rs.getBoolean("isBand"),
            rs.getBoolean("isEnabled"))
            .id(rs.getLong("id")).surname(rs.getString("surname"))
            .description(rs.getString("description"));

    @Before
    public void setUp() {
        super.setUp();
        insertUser(USER_ARTIST_ID, USER_ARTIST_EMAIL, USER_PASSWORD, USER_NAME, USER_ARTIST_SURNAME, USER_ARTIST_ISBAND, USER_ISENABLED_DISABLED, USER_DESCRIPTION_NULL);
    }

    @Test
    public void testGetUserById(){
        final Optional<User> user = userDao.getUserById(USER_ARTIST_ID);
        assertNotNull(user);
        assertTrue(user.isPresent());
        assertEquals(USER_ARTIST_ID, user.get().getId());
        assertEquals(USER_ARTIST_EMAIL, user.get().getEmail());
    }

    @Test
    public void testFindByEmail(){
        final Optional<User> user = userDao.findByEmail(USER_ARTIST_EMAIL);
        assertNotNull(user);
        assertTrue(user.isPresent());
        assertEquals(USER_ARTIST_ID, user.get().getId());
        assertEquals(USER_ARTIST_EMAIL, user.get().getEmail());
    }

}
