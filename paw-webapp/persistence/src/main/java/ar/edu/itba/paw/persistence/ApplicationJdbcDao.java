package ar.edu.itba.paw.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

@Repository
public class ApplicationJdbcDao implements ApplicationDao {

    @Autowired
    public ApplicationJdbcDao(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    private final JdbcTemplate jdbcTemplate;

    private final static RowMapper<Application.ApplicationBuilder> APPLICATION_ROW_MAPPER =
            (rs, i) -> new Application.ApplicationBuilder(
            rs.getLong("auditionId"),
            rs.getLong("applicantId"),
            ApplicationState.valueOf(rs.getString("state"))
    ).applicantName(rs.getString("name")).applicantSurname("surname");

    private final String GET_APPLICATION_QUERY = "SELECT auditionId,applicantId,state,name,surname FROM applications" +
            " JOIN users ON applications.applicantId = users.id" +
            " JOIN auditions ON applications.auditionId = auditions.id" +
            " WHERE auditionId IN (SELECT id FROM auditions WHERE bandId = ?)";

    @Override
    public List<Application> getApplicationsByState(long bandId, ApplicationState state) {
        StringBuilder sb = new StringBuilder(GET_APPLICATION_QUERY).append(" AND state = ?");
        final String sqlQuery = sb.toString();
        List<Application.ApplicationBuilder> list = jdbcTemplate.query(sqlQuery
                , new Object[]{bandId, state.getState().toUpperCase(Locale.ROOT)}, APPLICATION_ROW_MAPPER);
        List<Application> toReturn = new LinkedList<>();
        for(Application.ApplicationBuilder applicationBuilder : list) {
            toReturn.add(applicationBuilder.build());
        }
        return toReturn;
    }

    @Override
    public List<Application> getAllApplications(long bandId) {
        List<Application.ApplicationBuilder> list = jdbcTemplate.query(GET_APPLICATION_QUERY
                , new Object[]{bandId}, APPLICATION_ROW_MAPPER);
        List<Application> toReturn = new LinkedList<>();
        for(Application.ApplicationBuilder applicationBuilder : list) {
            toReturn.add(applicationBuilder.build());
        }
        return toReturn;
    }
}
