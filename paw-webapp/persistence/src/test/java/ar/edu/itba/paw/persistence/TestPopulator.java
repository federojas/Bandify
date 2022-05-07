package ar.edu.itba.paw.persistence;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
@Transactional
@Rollback
public class TestPopulator {

    @Autowired
    private DataSource ds;
    protected JdbcTemplate jdbcTemplate;

    protected final long USER_ARTIST_ID = 1;
    protected final long USER_BAND_ID = 2;
    protected final String USER_ARTIST_EMAIL = "artist@mail.com";
    protected final String USER_BAND_EMAIL = "band@mail.com";
    protected final String USER_PASSWORD = "12345678";
    protected final String USER_NAME = "name";
    protected final String USER_ARTIST_SURNAME = "surname";
    protected final String USER_BAND_SURNAME = null;
    protected final boolean USER_ARTIST_ISBAND = false;
    protected final boolean USER_BAND_ISBAND = true;
    protected final boolean USER_ISENABLED_ENABLED = true;
    protected final boolean USER_ISENABLED_DISABLED = false;
    protected final String USER_DESCRIPTION_NULL = null;
    protected final String USER_DESCRIPTION = "description";

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    protected void insertUser(long userId, String email, String password, String name, String surname, boolean isBand, boolean isEnabled, String description) {
        SimpleJdbcInsert subjectJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("users");
        Map<String, Object> args = new HashMap<>();
        args.put("id", userId);
        args.put("email", email);
        args.put("password", password);
        args.put("name", name);
        args.put("surname", surname);
        args.put("isBand", isBand);
        args.put("isEnabled", isEnabled);
        args.put("description", description);
        subjectJdbcInsert.execute(args);
    }

    protected void insertUserImage(long userId, byte[] image) {
        SimpleJdbcInsert subjectJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("profileimages");
        Map<String, Object> args = new HashMap<>();
        args.put("userid", userId);
        args.put("image", image);
        subjectJdbcInsert.execute(args);
    }

    protected void insertNewRole(long id, String role) {
        SimpleJdbcInsert subjectJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("roles");
        Map<String, Object> args = new HashMap<>();
        args.put("id", id);
        args.put("role", role);
        subjectJdbcInsert.execute(args);
    }

    protected void insertNewGenre(long id, String genre) {
        SimpleJdbcInsert subjectJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("genres");
        Map<String, Object> args = new HashMap<>();
        args.put("id", id);
        args.put("genre", genre);
        subjectJdbcInsert.execute(args);
    }

    protected void insertNewLocation(long id, String location) {
        SimpleJdbcInsert subjectJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("locations");
        Map<String, Object> args = new HashMap<>();
        args.put("id", id);
        args.put("genre", location);
        subjectJdbcInsert.execute(args);
    }

    protected void insertVerificationToken(long tokenId, long userId, String token, LocalDateTime date, String type) {
        SimpleJdbcInsert subjectJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("verificationtokens");
        Map<String, Object> args = new HashMap<>();
        args.put("tokenid", tokenId);
        args.put("userid", userId);
        args.put("token", token);
        args.put("expirydate", date);
        args.put("type", type);
        subjectJdbcInsert.execute(args);
    }

    protected void insertAuditionGenre(long auditionid, long genreid) {
        SimpleJdbcInsert subjectJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("auditiongenres");
        Map<String, Object> args = new HashMap<>();
        args.put("auditionid", auditionid);
        args.put("genreid", genreid);
        subjectJdbcInsert.execute(args);
    }

    protected void insertAuditionRole(long auditionid, long roleid) {
        SimpleJdbcInsert subjectJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("auditionroles");
        Map<String, Object> args = new HashMap<>();
        args.put("auditionid", auditionid);
        args.put("roleid", roleid);
        subjectJdbcInsert.execute(args);
    }

    protected void insertAudition(long id, long bandid, String title, String description, LocalDateTime creationdate, long locationid) {
        SimpleJdbcInsert subjectJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("auditions");
        Map<String, Object> args = new HashMap<>();
        args.put("id", id);
        args.put("bandid", bandid);
        args.put("title", title);
        args.put("description", description);
        args.put("creationdate", creationdate);
        args.put("locationid", locationid);
        subjectJdbcInsert.execute(args);
    }
}
