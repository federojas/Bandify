package ar.edu.itba.paw.persistence;
import ar.edu.itba.paw.model.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class GenreJdbcDao implements GenreDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcGenreInsert;
    private final static RowMapper<Genre> GENRE_ROW_MAPPER = (rs, i) -> new Genre(rs.getLong("id"), rs.getString("genre"));

    @Autowired
    public GenreJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcGenreInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("auditionGenres");
    }

    @Override
    public List<Genre> getAll() {
        return jdbcTemplate.query("SELECT * FROM genres", GENRE_ROW_MAPPER);
    }

    @Override
    public List<Genre> getGenresByAuditionId(long auditionId) {
        return jdbcTemplate.query("SELECT genres.id, genres.genre FROM AUDITIONGENRES JOIN GENRES ON auditiongenres.genreid = genres.id WHERE auditionId = ?", new Object[]{auditionId}, GENRE_ROW_MAPPER);
    }

    @Override
    public void createAuditionGenre(List<Genre> genres, long auditionId) {
        final Map<String, Object> audGenreData = new HashMap<>();
        audGenreData.put("auditionId", auditionId);
        audGenreData.put("genreId", 0);
        for(Genre genre : genres) {
            audGenreData.replace("genreId", genre.getId());
            jdbcGenreInsert.execute(audGenreData);
        }
    }

    @Override
    public Optional<Genre> getGenreById(Long id) {
        return jdbcTemplate.query("SELECT * FROM genres WHERE id = ? ", new Object[]{id}, GENRE_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public Optional<Genre> getGenreByName(String name) {
        return jdbcTemplate.query("SELECT * FROM genres WHERE genre = ? ", new Object[]{name}, GENRE_ROW_MAPPER).stream().findFirst();
    }
}
