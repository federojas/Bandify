package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exceptions.UserNotFoundException;
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

    private final static RowMapper<User.UserBuilder> ROW_MAPPER = (rs, rowNum) -> new User.UserBuilder(rs.getString("email"),
            rs.getString("password"),
            rs.getString("name"),
            rs.getBoolean("isBand"))
            .id(rs.getLong("id")).surname(rs.getString("surname"));

    @Autowired
    public UserJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("users").usingGeneratedKeyColumns("id");
    }

    @Override
    public Optional<User> getUserById(final long id) {
        final List<User.UserBuilder> list = jdbcTemplate.query("SELECT * FROM users WHERE id = ?", new Object[] { id }, ROW_MAPPER);
        Optional<User.UserBuilder> op = list.stream().findFirst();
        if (op.isPresent()) {
            User user = op.get().build();
            return Optional.of(user);
        }
        return Optional.empty();
    }

    @Override
    public User create(User.UserBuilder userBuilder) {
        final Map<String, Object> userData = new HashMap<>();

        userData.put("email", userBuilder.getEmail());
        userData.put("password", userBuilder.getPassword());
        userData.put("name", userBuilder.getName());
        userData.put("surname", userBuilder.getSurname());
        userData.put("isBand", userBuilder.isBand());

        final Number id = jdbcInsert.executeAndReturnKey(userData);

        return userBuilder.id(id.longValue()).build();

    }

    @Override
    public Optional<User> findByEmail(String email) {
        final List<User.UserBuilder> list = jdbcTemplate.query("SELECT * FROM users WHERE email = ?", new Object[] { email }, ROW_MAPPER);
        Optional<User.UserBuilder> op = list.stream().findFirst();
        if (op.isPresent()) {
            User user = op.get().build();
            return Optional.of(user);
        }
        return Optional.empty();
    }

    @Override
    public void changePassword(long userId, String newPassword) {
        jdbcTemplate.update("UPDATE users SET password = ? WHERE id = ?", newPassword,userId);
    }

}
