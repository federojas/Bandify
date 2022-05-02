package ar.edu.itba.paw.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class ApplicationJdbcDao implements ApplicationDao {

    @Autowired
    public ApplicationJdbcDao(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("applications");
    }

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

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
        return list.stream().map(Application.ApplicationBuilder::build).collect(Collectors.toList());
    }

    @Override
    public List<Application> getAllApplications(long bandId) {
        List<Application.ApplicationBuilder> list = jdbcTemplate.query(GET_APPLICATION_QUERY
                , new Object[]{bandId}, APPLICATION_ROW_MAPPER);
        return list.stream().map(Application.ApplicationBuilder::build).collect(Collectors.toList());
    }

    @Override
    public List<Application> getAuditionApplications(long auditionId) {
        List<Application.ApplicationBuilder> list = jdbcTemplate.query("SELECT auditionId,applicantId,state,name,surname FROM applications" +
                        " JOIN users ON applications.applicantId = users.id" +
                        " JOIN auditions ON applications.auditionId = auditions.id" +
                        " WHERE auditionId = ?"
                , new Object[]{auditionId}, APPLICATION_ROW_MAPPER);
        return list.stream().map(Application.ApplicationBuilder::build).collect(Collectors.toList());
    }

    @Override
    public List<Application> getAuditionApplicationsByState(long auditionId, ApplicationState state) {
        List<Application.ApplicationBuilder> list = jdbcTemplate.query("SELECT auditionId,applicantId,state,name,surname FROM applications" +
                        " JOIN users ON applications.applicantId = users.id" +
                        " JOIN auditions ON applications.auditionId = auditions.id" +
                        " WHERE auditionId = ? AND state = ?"
                , new Object[]{auditionId, state.getState().toUpperCase(Locale.ROOT)}, APPLICATION_ROW_MAPPER);
        return list.stream().map(Application.ApplicationBuilder::build).collect(Collectors.toList());
    }

    @Override
    public Application createApplication(Application.ApplicationBuilder applicationBuilder) {
        System.out.println("la cree");
        final Map<String, Object> applicationData = new HashMap<>();
        applicationData.put("auditionId", applicationBuilder.getAuditionId());
        applicationData.put("applicantId", applicationBuilder.getApplicantId());
        applicationData.put("state", applicationBuilder.getState().getState().toUpperCase(Locale.ROOT));
        jdbcInsert.execute(applicationData);
        return applicationBuilder.build();
    }
}
