package ar.edu.itba.paw.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ApplicationJdbcDao implements ApplicationDao {

    private final int PAGE_SIZE = 10;

    @Autowired
    public ApplicationJdbcDao(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("applications");
    }

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private final static RowMapper<Integer> TOTAL_APPLICATION_ROW_MAPPER = (rs, i) -> rs.getInt("applicationTotal");

    private final static RowMapper<Application.ApplicationBuilder> APPLICATION_ROW_MAPPER =
            (rs, i) -> new Application.ApplicationBuilder(
            rs.getLong("auditionId"),
            rs.getLong("applicantId"),
            ApplicationState.valueOf(rs.getString("state"))
            ).applicantName(rs.getString("name"))
             .applicantSurname(rs.getString("surname"))
             .auditionTitle(rs.getString("title"));

    private final static RowMapper<Boolean> EXISTS_ROW_MAPPER = ((resultSet, i) -> resultSet.getBoolean("exists"));

    private final String GET_APPLICATION_QUERY = "SELECT auditionId,applicantId,state,name,surname,title FROM applications" +
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
        List<Application.ApplicationBuilder> list = jdbcTemplate.query("SELECT auditionId,applicantId,state,name,surname,title FROM applications" +
                        " JOIN users ON applications.applicantId = users.id" +
                        " JOIN auditions ON applications.auditionId = auditions.id" +
                        " WHERE auditionId = ?"
                , new Object[]{auditionId}, APPLICATION_ROW_MAPPER);
        return list.stream().map(Application.ApplicationBuilder::build).collect(Collectors.toList());
    }


    @Override
    public List<Application> getAuditionApplicationsByState(long auditionId, ApplicationState state) {
        List<Application.ApplicationBuilder> list = jdbcTemplate.query("SELECT auditionId,applicantId,state,name,surname,title FROM applications" +
                        " JOIN users ON applications.applicantId = users.id" +
                        " JOIN auditions ON applications.auditionId = auditions.id" +
                        " WHERE auditionId = ? AND state = ?"
                , new Object[]{auditionId, state.getState().toUpperCase(Locale.ROOT)}, APPLICATION_ROW_MAPPER);
        return list.stream().map(Application.ApplicationBuilder::build).collect(Collectors.toList());
    }

    @Override
    public Application createApplication(Application.ApplicationBuilder applicationBuilder) {
        final Map<String, Object> applicationData = new HashMap<>();
        applicationData.put("auditionId", applicationBuilder.getAuditionId());
        applicationData.put("applicantId", applicationBuilder.getApplicantId());
        applicationData.put("state", applicationBuilder.getState().getState().toUpperCase(Locale.ROOT));
        jdbcInsert.execute(applicationData);
        return applicationBuilder.build();
    }

    @Override
    public void setApplicationState(long auditionId, long applicantId, ApplicationState state) {
        jdbcTemplate.update("UPDATE applications SET state = ? WHERE applicantId = ? AND auditionId = ?",
                new Object[]{state.getState().toUpperCase(Locale.ROOT), applicantId, auditionId});
    }

    // TODO: revisar

    @Override
    public List<Application> getMyApplications(long applicantId, int page) {
        String query = "SELECT auditionId,applicantId,state,name,surname,title FROM applications" +
                " JOIN users ON applications.applicantId = users.id" +
                " JOIN auditions ON applications.auditionId = auditions.id" +
                " WHERE applicantId = ? ORDER BY auditionId DESC LIMIT ? OFFSET ?";
        List<Application.ApplicationBuilder> list = jdbcTemplate.query(query,new Object[]{applicantId, PAGE_SIZE, (page -1) * PAGE_SIZE},APPLICATION_ROW_MAPPER);
        return list.stream().map(Application.ApplicationBuilder::build).collect(Collectors.toList());
    }

    @Override
    public boolean exists(long auditionId, long id) {
        return jdbcTemplate.query("SELECT EXISTS (SELECT * FROM applications WHERE auditionId=? AND applicantId=?)", new Object[]{auditionId,id}, EXISTS_ROW_MAPPER).stream().findFirst().orElse(false);
    }

    @Override
    public int getTotalUserApplicationPages(long userId) {
        Optional<Integer> result = jdbcTemplate.query("SELECT COUNT(*) AS applicationTotal FROM applications WHERE applicantId = ?", new Object[] {userId} ,TOTAL_APPLICATION_ROW_MAPPER).stream().findFirst();
        return result.map(integer -> (int) Math.ceil(integer.doubleValue() / PAGE_SIZE)).orElse(0);
    }
}
