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
public class GenreJdbcDao implements GenreDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcUserGenreInsert;
    private final static RowMapper<Genre> GENRE_ROW_MAPPER = (rs, i) -> new Genre(rs.getLong("id"), rs.getString("genre"));

    @Autowired
    public GenreJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcUserGenreInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("userGenres");
    }

    @Override
    public Set<Genre> getAll() {
        return jdbcTemplate.query("SELECT * FROM genres", GENRE_ROW_MAPPER).stream().collect(Collectors.toSet());
    }

    @Override
    public Set<Genre> getGenresByAuditionId(long auditionId) {
        return jdbcTemplate.query("SELECT genres.id, genres.genre FROM AUDITIONGENRES JOIN GENRES ON auditiongenres.genreid = genres.id WHERE auditionId = ?", new Object[]{auditionId}, GENRE_ROW_MAPPER).stream().collect(Collectors.toSet());
    }

    @Override
    public Optional<Genre> getGenreById(Long id) {
        return jdbcTemplate.query("SELECT * FROM genres WHERE id = ? ", new Object[]{id}, GENRE_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public Optional<Genre> getGenreByName(String name) {
        return jdbcTemplate.query("SELECT * FROM genres WHERE genre = ? ", new Object[]{name}, GENRE_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public Set<Genre> getGenresByNames(List<String> genresNames) {
        String inSql = String.join(",", Collections.nCopies(genresNames.size(), "?"));
        return jdbcTemplate.query(String.format("SELECT * FROM genres WHERE genre IN (%s)", inSql), genresNames.toArray(),GENRE_ROW_MAPPER).stream().collect(Collectors.toSet());
    }

    @Override
    public Set<Genre> getUserGenres(long userId) {
        return jdbcTemplate.query("SELECT genres.id,genres.genre FROM genres JOIN userGenres ON genres.id = userGenres.genreId JOIN users ON userGenres.userId = users.id WHERE users.id = ? ", new Object[]{userId} , GENRE_ROW_MAPPER).stream().collect(Collectors.toSet());
    }

    @Override
    public void updateUserGenres(Set<Genre> newGenres, long userId) {
        jdbcTemplate.update("DELETE FROM userGenres WHERE userId = ?", new Object[]{userId});
        if(newGenres != null) {
            final Map<String, Object> userGenreData = new HashMap<>();
            userGenreData.put("userId", userId);
            userGenreData.put("genreId", 0);
            for (Genre genre : newGenres) {
                userGenreData.replace("genreId", genre.getId());
                jdbcUserGenreInsert.execute(userGenreData);
            }
        }
    }
}
