package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Audition;
import com.sun.istack.internal.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import java.sql.Array;
import java.util.*;

@Repository
public class AuditionJdbcDao implements AuditionDao {

    private JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcAuditionInsert;
    private final SimpleJdbcInsert jdbcRoleInsert;
    private final SimpleJdbcInsert jdbcGenreInsert;

    private final static RowMapper<String> GENRE_ROW_MAPPER = (rs, i) -> rs.getString("genre");

    private final static RowMapper<String> ROLE_ROW_MAPPER = (rs, i) -> rs.getString("role");

    private final static RowMapper<Audition> AUDITION_ROW_MAPPER = (rs, i) -> {

        return new Audition(rs.getLong("id"),
                rs.getLong("bandId"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("location"),
                rs.getDate("creationDAte"),
                null,
                null);
    };


    @Autowired
    public AuditionJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcAuditionInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("auditions").usingGeneratedKeyColumns("id");
        jdbcRoleInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("auditionRoles").usingGeneratedKeyColumns("id");
        jdbcGenreInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("auditionGenres").usingGeneratedKeyColumns("id");

        jdbcTemplate.execute( "CREATE TABLE IF NOT EXISTS auditions"
                        + "("
                        + "id SERIAL PRIMARY KEY,"
                        + "bandId INT NOT NULL,"
                        + "title VARCHAR(100) UNIQUE NOT NULL,"
                        + "description VARCHAR(512) NOT NULL,"
                        + "creationDate DATE NOT NULL,"
                        + "location VARCHAR(100) NOT NULL"
                        + ")"
                // TODO : FOREIGN KEY(band_id) REFERENCES Band(band_id) ON DELETE CASCADE
        );
        jdbcTemplate.execute( "CREATE TABLE IF NOT EXISTS auditionGenres"
                + "("
                + "id INT NOT NULL,"
                + "genre VARCHAR(50) NOT NULL,"
                + "PRIMARY KEY(id, genre),"
                + "FOREIGN KEY(id) REFERENCES auditions(id) ON DELETE CASCADE"
                + ")"
        );

        jdbcTemplate.execute( "CREATE TABLE IF NOT EXISTS auditionRoles"
                + "("
                + "id INT NOT NULL,"
                + "role VARCHAR(50) NOT NULL,"
                + "PRIMARY KEY(id, role),"
                + "FOREIGN KEY(id) REFERENCES auditions(id) ON DELETE CASCADE"
                + ")"
        );

    }

    @Override
    public Optional<Audition> getAuditionById(long id) {
        final List<String> genres = jdbcTemplate.query("SELECT genre FROM auditionGenres WHERE id = ?", new Object[]{id}, GENRE_ROW_MAPPER);
        final List<String> roles = jdbcTemplate.query("SELECT role FROM auditionRoles WHERE id = ?", new Object[]{id}, ROLE_ROW_MAPPER);
        final List<Audition> auditions = jdbcTemplate.query("SELECT * FROM auditions WHERE id = ?", new Object[]{id}, AUDITION_ROW_MAPPER);
        Optional<Audition> toReturn = auditions.stream().findFirst();

        if(toReturn.isPresent()) {
            toReturn.get().setLookingFor(roles);
            toReturn.get().setMusicGenres(genres);
        }

        return toReturn;
    }

    @Override
    public Audition create(String title, String description, String location, Date creationDate, List<String> musicGenres, List<String> lookingFor) {
        final Map<String, Object> auditionData = new HashMap<>();
        auditionData.put("title", title);
        auditionData.put("description", description);
        auditionData.put("location", location);
        auditionData.put("date", creationDate);
        auditionData.put("musicGenres", musicGenres);
        auditionData.put("lookingFor", lookingFor);
        final Number id = jdbcAuditionInsert.executeAndReturnKey(auditionData);
        // TODO: BandId?
        return new Audition(id.longValue(),3, title, description, location, creationDate, musicGenres, lookingFor);
    }
    //TODO: no lo tocamos al crear tablas genres y roles

    @Override
    public List<Audition> getAll(int page) {
        return jdbcTemplate.query("SELECT * FROM auditions LIMIT 10 OFFSET ?", new Object[] { (page -1) * 10}, AUDITION_ROW_MAPPER);
    }
}
