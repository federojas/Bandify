package ar.edu.itba.paw.persistence;
import ar.edu.itba.paw.model.Role;
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
    private final SimpleJdbcInsert jdbcRoleInsert;
    private final SimpleJdbcInsert jdbcUserRoleInsert;
    private final static RowMapper<Role> ROLE_ROW_MAPPER = (rs, i) -> new Role(rs.getLong("id"), rs.getString("role"));

    @Autowired
    public RoleJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcRoleInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("auditionRoles");
        jdbcUserRoleInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("userRoles");
    }

    @Override
    public Set<Role> getAll() {
        return jdbcTemplate.query("SELECT * FROM roles", ROLE_ROW_MAPPER).stream().collect(Collectors.toSet());
    }

    @Override
    public Set<Role> getRolesByAuditionId(long auditionId) {
        return jdbcTemplate.query("SELECT roles.id, roles.role FROM AUDITIONROLES JOIN ROLES ON auditionroles.roleid = roles.id WHERE auditionId = ?", new Object[]{auditionId}, ROLE_ROW_MAPPER).stream().collect(Collectors.toSet());
    }

    @Override
    public void createAuditionRole(Set<Role> roles, long auditionId) {
        final Map<String, Object> audRoleData = new HashMap<>();
        audRoleData.put("auditionId", auditionId);
        audRoleData.put("roleId", 0);
        for(Role role : roles) {
            audRoleData.replace("roleId", role.getId());
            jdbcRoleInsert.execute(audRoleData);
        }
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
        return jdbcTemplate.query("SELECT roles.id,roles.role FROM roles JOIN userRoles ON roles.id = userRoles.roleId JOIN users ON userRoles.userId = users.id",ROLE_ROW_MAPPER).stream().collect(Collectors.toSet());
    }

    @Override
    public void addUserRoles(List<String> rolesNames, long userId) {
        Set<Role> roles = getRolesByNames(rolesNames);
        final Map<String, Object> userRoleData = new HashMap<>();
        userRoleData.put("userId", userId);
        userRoleData.put("roleId", 0);
        for(Role role : roles) {
            userRoleData.replace("roleId", role.getId());
            jdbcUserRoleInsert.execute(userRoleData);
        }
    }

    @Override
    public void updateUserRoles(Set<Role> newRoles, long userId) {
        // TODO: terminar
        for(Role role : newRoles) {
            jdbcTemplate.update("INSERT INTO userRoles SELECT ?,? WHERE NOT EXISTS (SELECT userId,roleId FROM userRoles WHERE userId = ? and roleId = ? );",
                            new Object[]{userId,role.getId(),userId,role.getId()});
        }
    }
}
