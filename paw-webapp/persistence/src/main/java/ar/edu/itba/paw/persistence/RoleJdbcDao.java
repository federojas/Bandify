package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class RoleJdbcDao implements RoleDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcUserRoleInsert;
    private final static RowMapper<Role> ROLE_ROW_MAPPER = (rs, i) -> new Role(rs.getLong("id"), rs.getString("role"));
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleJdbcDao.class);
    @Autowired
    public RoleJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcUserRoleInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("userRoles");
    }

    @Override
    public Set<Role> getAll() {
        return jdbcTemplate.query("SELECT * FROM roles", ROLE_ROW_MAPPER).stream().collect(Collectors.toSet());
    }

    @Override
    public Optional<Role> getRoleById(Long id) {
        return jdbcTemplate.query("SELECT * FROM roles WHERE id = ? ", new Object[]{id}, ROLE_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public Optional<Role> getRoleByName(String name) {
        return jdbcTemplate.query("SELECT * FROM roles WHERE role = ? ", new Object[]{name}, ROLE_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public Set<Role> getRolesByNames(List<String> rolesNames) {
        String inSql = String.join(",", Collections.nCopies(rolesNames.size(), "?"));
        return jdbcTemplate.query(String.format("SELECT * FROM roles WHERE role IN (%s)", inSql), rolesNames.toArray(),ROLE_ROW_MAPPER).stream().collect(Collectors.toSet());
    }

    @Override
    public Set<Role> getUserRoles(long userId) {
        return jdbcTemplate.query("SELECT roles.id,roles.role FROM roles JOIN userRoles ON roles.id = userRoles.roleId JOIN users ON userRoles.userId = users.id WHERE users.id = ?", new Object[]{userId}, ROLE_ROW_MAPPER).stream().collect(Collectors.toSet());
    }

    @Override
    public void updateUserRoles(Set<Role> newRoles, long userId) {
        jdbcTemplate.update("DELETE FROM userRoles WHERE userId = ?", new Object[] {userId});
        if(newRoles != null) {
            final Map<String, Object> userRoleData = new HashMap<>();
            userRoleData.put("userId", userId);
            userRoleData.put("roleId", 0);
            for (Role role : newRoles) {
                userRoleData.replace("roleId", role.getId());
                jdbcUserRoleInsert.execute(userRoleData);
                LOGGER.info("Role {} added to user {}", role.getName(),userId);
            }
        }
    }
}
