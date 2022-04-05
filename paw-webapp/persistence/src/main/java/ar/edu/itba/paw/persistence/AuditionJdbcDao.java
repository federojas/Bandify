package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Audition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Date;
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
        jdbcRoleInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("auditionRoles");
        jdbcGenreInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("auditionGenres");
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
        auditionData.put("bandid",3);
        auditionData.put("description", description);
        auditionData.put("location", location);
        auditionData.put("creationDate", creationDate);
        final Number id = jdbcAuditionInsert.executeAndReturnKey(auditionData);

        final Map<String, Object> auditionGenres = new HashMap<>();
        auditionGenres.put("id",0);
        auditionGenres.put("genre","mock");

        for(String genre : musicGenres) {
            auditionGenres.replace("id",id);
            auditionGenres.replace("genre",genre);
            jdbcGenreInsert.execute(auditionGenres);
        }

        final Map<String, Object> auditionRoles = new HashMap<>();
        auditionRoles.put("id",0);
        auditionRoles.put("role","mock");

        for(String role : lookingFor) {
            auditionRoles.replace("id",id);
            auditionRoles.replace("role",role);
            jdbcRoleInsert.execute(auditionRoles);
        }
        // TODO: BandId?
        return new Audition(id.longValue(),3, title, description, location, creationDate, musicGenres, lookingFor);
    }
    //TODO: no lo tocamos al crear tablas genres y roles

    @Override
    public List<Audition> getAll(int page) {
//        List<Audition> auditions = new ArrayList<>();
//        for(int i = (page -1) * 10; i < 10 ; i++) {
//            Optional<Audition> audition = getAuditionById(i);
//            audition.ifPresent(auditions::add);
//        }
        List<Audition> auditions = jdbcTemplate.query("SELECT * FROM auditions LIMIT 10 OFFSET ?", new Object[] { (page -1) * 10}, AUDITION_ROW_MAPPER);
        List<String> genres;
        List<String> roles;
        for(int i = 0 ; i < auditions.size(); i++) {
            genres = jdbcTemplate.query("SELECT genre FROM auditionGenres WHERE id = ?", new Object[]{auditions.get(i).getId()}, GENRE_ROW_MAPPER);
            roles = jdbcTemplate.query("SELECT role FROM auditionRoles WHERE id = ?", new Object[]{auditions.get(i).getId()}, ROLE_ROW_MAPPER);
            auditions.get(i).setMusicGenres(genres);
            auditions.get(i).setLookingFor(roles);
        }
        return auditions;
    }
}
