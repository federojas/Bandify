package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class LocationJdbcDao implements LocationDao {

    private final JdbcTemplate jdbcTemplate;
    private final static RowMapper<Location> LOCATION_ROW_MAPPER = (rs, i) -> new Location(rs.getLong("id"), rs.getString("location"));

    @Autowired
    public LocationJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public List<Location> getAll() {
        return jdbcTemplate.query("SELECT * FROM locations", LOCATION_ROW_MAPPER);
    }

    @Override
    public Optional<Location> getLocationByName(String name) {
        return jdbcTemplate.query("SELECT * FROM LOCATIONS WHERE location = ?", new Object[]{name},LOCATION_ROW_MAPPER).stream().findFirst();
    }
}
