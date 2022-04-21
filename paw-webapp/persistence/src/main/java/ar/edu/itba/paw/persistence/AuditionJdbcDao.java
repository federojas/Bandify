
package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.exceptions.LocationNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class AuditionJdbcDao implements AuditionDao {


    private JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcAuditionInsert;
    private final GenreDao genreDao;
    private final RoleDao roleDao;
    private final LocationDao locationDao;

    private final int PAGE_SIZE = 9;

    @Autowired
    public AuditionJdbcDao(final DataSource ds, GenreDao genreDao, RoleDao roleDao, LocationDao locationDao) {
        jdbcTemplate = new JdbcTemplate(ds);
        this.genreDao = genreDao;
        this.roleDao = roleDao;
        this.locationDao = locationDao;
        jdbcAuditionInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("auditions").usingGeneratedKeyColumns("id");
    }

    private final static RowMapper<Audition.AuditionBuilder> AUDITION_ROW_MAPPER = (rs, i) -> {
        return new Audition.AuditionBuilder(
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("email"),
                rs.getLong("bandId"),
                rs.getTimestamp("creationDate").toLocalDateTime()
                ).id(rs.getLong("id"));
    };

    private final static RowMapper<Integer> TOTAL_AUDITION_ROWMAPPER = (rs, i) -> rs.getInt("count");

    @Override
    public Optional<Audition> getAuditionById(long id) {
        final List<Audition.AuditionBuilder> auditionsBuilders = jdbcTemplate.query("SELECT * FROM auditions WHERE id = ?", new Object[]{id}, AUDITION_ROW_MAPPER);
        Optional<Audition.AuditionBuilder> builder = auditionsBuilders.stream().findFirst();
        if(builder.isPresent()) {
            Audition toReturn = builder.get().musicGenres(genreDao.getGenresByAuditionId(id))
                    .lookingFor(roleDao.getRolesByAuditionId(id)).
                    location(locationDao.getLocationByAuditionId(id).orElseThrow(LocationNotFoundException::new)).build();
            return Optional.of(toReturn);
        }
        return Optional.empty();
    }

    @Override
    public Audition create(Audition.AuditionBuilder builder) {
        final Map<String, Object> auditionData = new HashMap<>();

        auditionData.put("title", builder.getTitle());
        auditionData.put("bandid", builder.getBandId());
        auditionData.put("description", builder.getDescription());
        auditionData.put("locationId", builder.getLocation().getId());
        auditionData.put("creationDate", builder.getCreationDate());
        auditionData.put("email", builder.getEmail());

        final Number id = jdbcAuditionInsert.executeAndReturnKey(auditionData);

        roleDao.createAuditionRole(builder.getLookingFor(),id.longValue());
        genreDao.createAuditionGenre(builder.getMusicGenres(), id.longValue());

        return builder.id(id.longValue()).build();
    }

    @Override
    public List<Audition> getAll(int page) {
        List<Audition.AuditionBuilder> auditionsBuilders = jdbcTemplate.query("SELECT * FROM auditions ORDER BY creationdate DESC, title ASC LIMIT ? OFFSET ? " ,new Object[] { PAGE_SIZE, (page -1) * PAGE_SIZE}, AUDITION_ROW_MAPPER);
        return getAuditions(auditionsBuilders);
    }

    @Override
    public List<Audition> search(int page, String query) {
        List<Audition.AuditionBuilder> auditionsBuilders = jdbcTemplate.query("SELECT * FROM auditions WHERE title LIKE ? ORDER BY creationdate DESC, title ASC LIMIT ? OFFSET ? " ,new Object[] {"%" + query + "%", PAGE_SIZE, (page -1) * PAGE_SIZE}, AUDITION_ROW_MAPPER);
        return getAuditions(auditionsBuilders);
    }

    private List<Audition> getAuditions(List<Audition.AuditionBuilder> auditionsBuilders) {
        List<Audition> toReturn = new ArrayList<>();
        for(Audition.AuditionBuilder builder : auditionsBuilders) {
            Optional<Audition> toAdd = getAuditionById(builder.getId());
            toAdd.ifPresent(toReturn::add);
        }
        return toReturn;
    }

    @Override
    public int getTotalAuditions() {
        Optional<Integer> result = jdbcTemplate.query("SELECT COUNT(*) FROM auditions", TOTAL_AUDITION_ROWMAPPER).stream().findFirst();
        //TODO Math.ceil casteado a int puede castear un double muy grande y generar una excepcion
        //TODO tamaño int es la maxima page
        return result.map(integer -> (int) Math.ceil(integer.doubleValue() / PAGE_SIZE)).orElse(0);
    }
    @Override
    public long getMaxAuditionId() {
        return jdbcTemplate.query("SELECT max(id) FROM auditions", (rs,i) -> rs.getLong("max") ).stream().findFirst().orElse(0L);
    }

}
