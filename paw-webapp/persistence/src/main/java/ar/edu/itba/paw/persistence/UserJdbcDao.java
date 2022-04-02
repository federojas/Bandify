package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserJdbcDao implements UserDao {

    private JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private final static RowMapper<User> ROW_MAPPER = (rs, rowNum) -> new User(rs.getInt("userid"),rs.getString("username"), rs.getString("password"));

    @Autowired
    public UserJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("users").usingGeneratedKeyColumns("userid");

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS users " +
                "("
                + "userid SERIAL PRIMARY KEY,"
                + "username varchar(100),"
                + "password varchar(100)"
                + ")"
        );
    }

    @Override
    public Optional<User> getUserById(final long id) {
        final List<User> list = jdbcTemplate.query("SELECT * FROM users WHERE userid = ?", ROW_MAPPER, id);
        return list.stream().findFirst();
    }

    public List<User> getAll(int page) {
        return jdbcTemplate.query("SELECT * FROM Users LIMIT 10 OFFSET ?", new Object[] { (page -1) * 10}, ROW_MAPPER);
    }

    @Override
    public User create(String username, String password) {
        final Map<String, Object> userData = new HashMap<>();
        userData.put("username", username);
        userData.put("password", password);
        final Number userId = jdbcInsert.executeAndReturnKey(userData);
        System.out.println("LLEGUE2");
        for(User user : getAll(1)){
            System.out.println(user.getUsername());
        }
        System.out.println();
        return new User(userId.longValue(), username, password);
    }
}
