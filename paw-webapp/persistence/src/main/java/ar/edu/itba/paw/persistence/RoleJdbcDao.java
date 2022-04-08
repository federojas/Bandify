package ar.edu.itba.paw.persistence;
import ar.edu.itba.paw.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RoleJdbcDao implements RoleDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcRoleInsert;
    private final static RowMapper<Role> ROLE_ROW_MAPPER = (rs, i) -> new Role(rs.getLong("id"), rs.getString("role"));

    @Autowired
    public RoleJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcRoleInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("auditionRoles");
    }

    @Override
    public List<Role> getAll() {
        return jdbcTemplate.query("SELECT * FROM roles", ROLE_ROW_MAPPER);
    }

    @Override
    public List<Role> getRolesByAuditionId(long auditionId) {
        return jdbcTemplate.query("SELECT roles.id, roles.role FROM AUDITIONROLES JOIN ROLES ON auditionroles.roleid = roles.id WHERE auditionId = ?", new Object[]{auditionId}, ROLE_ROW_MAPPER);
    }

    @Override
    public void createAuditionRole(List<Role> roles, long auditionId) {
        final Map<String, Object> audRoleData = new HashMap<>();
        audRoleData.put("auditionId", auditionId);
        audRoleData.put("roleId", 0);
        for(Role role : roles) {
            audRoleData.replace("roleId", role.getId());
            jdbcRoleInsert.execute(audRoleData);
        }
    }

    @Override
    public Role getRoleById(Long id) {
        return jdbcTemplate.query("SELECT * FROM roles WHERE id = ? ", new Object[]{id}, ROLE_ROW_MAPPER).get(0);
    }
}
