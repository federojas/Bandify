
package ar.edu.itba.paw.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class AuditionJdbcDao implements AuditionDao {

    private final JdbcTemplate jdbcTemplate;
    private final int PAGE_SIZE = 12;
    private final SimpleJdbcInsert jdbcAuditionInsert;
    private final GenreDao genreDao;
    private final RoleDao roleDao;

    private final static RowMapper<Genre> GENRE_ROW_MAPPER = (rs, i) -> new Genre(rs.getLong("id"), rs.getString("genre"));
    private final static RowMapper<Role> ROLE_ROW_MAPPER = (rs, i) -> new Role(rs.getLong("id"), rs.getString("role"));
    private final static RowMapper<Location> LOCATION_ROW_MAPPER = (rs, i) -> new Location(rs.getLong("id"), rs.getString("location"));
    private final static RowMapper<Audition.AuditionBuilder> AUDITION_ROW_MAPPER = (rs, i) -> {
        return new Audition.AuditionBuilder(
                rs.getString("title"),
                rs.getString("description"),
                rs.getLong("bandId"),
                rs.getTimestamp("creationDate").toLocalDateTime()
        ).id(rs.getLong("id"));
    };
    private final static ResultSetExtractor<List<Audition.AuditionBuilder>> AUDITION_MAPPER = rs -> {
        Map<Long, Audition.AuditionBuilder> auditionsById = new HashMap<>();
        int i = 0;
        while (rs.next()) {
            Long id = rs.getLong("id");
            Location location = LOCATION_ROW_MAPPER.mapRow(rs,i);
            Genre genre = GENRE_ROW_MAPPER.mapRow(rs,i);
            Role role = ROLE_ROW_MAPPER.mapRow(rs,i);
            Audition.AuditionBuilder auditionBuilder = auditionsById.getOrDefault(
                    id, AUDITION_ROW_MAPPER.mapRow(rs,i)
            ).location(location).lookingFor(role).musicGenres(genre);

            auditionsById.putIfAbsent(id,auditionBuilder);
            i++;
        }
        return auditionsById.values().stream().collect(Collectors.toList());
    };
    private final static RowMapper<Integer> TOTAL_AUDITION_ROW_MAPPER = (rs, i) -> rs.getInt("count");

    private final String GET_FULL_AUD_QUERY = "SELECT auditions.id,bandid,title,description,creationdate,location,genre,role" +
            " FROM auditions JOIN auditiongenres ON id = auditiongenres.auditionid JOIN auditionroles ON id = auditionroles.auditionid" +
            " JOIN locations ON auditions.locationid = locations.id JOIN genres ON genres.id = auditiongenres.genreid" +
            " JOIN roles ON roles.id = auditionroles.roleid";

    @Autowired
    public AuditionJdbcDao(final DataSource ds, GenreDao genreDao, RoleDao roleDao) {
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.genreDao = genreDao;
        this.roleDao = roleDao;
        this.jdbcAuditionInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("auditions").usingGeneratedKeyColumns("id");
    }

    @Override
    public Optional<Audition> getAuditionById(long id) {
        StringBuilder sb = new StringBuilder(GET_FULL_AUD_QUERY).append(" WHERE auditions.id = ?");
        final String query = sb.toString();
        List<Audition.AuditionBuilder> auditionsBuilders = jdbcTemplate.query(query,new Object[] { id }, AUDITION_MAPPER);
        Optional<Audition.AuditionBuilder> builder = auditionsBuilders.stream().findFirst();
        if(builder.isPresent()) {
            Audition toReturn = builder.get().build();
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
        final Number id = jdbcAuditionInsert.executeAndReturnKey(auditionData);

        roleDao.createAuditionRole(builder.getLookingFor(),id.longValue());
        genreDao.createAuditionGenre(builder.getMusicGenres(), id.longValue());

        return builder.id(id.longValue()).build();
    }

    @Override
    public List<Audition> getAll(int page) {
        StringBuilder sb = new StringBuilder(GET_FULL_AUD_QUERY).append(" WHERE auditions.id IN (SELECT id FROM auditions LIMIT ? OFFSET ?)" +
                " ORDER BY creationdate DESC, title ASC");
        final String query = sb.toString();
        List<Audition.AuditionBuilder> auditionsBuilders = jdbcTemplate.query(query,new Object[] { PAGE_SIZE, (page -1) * PAGE_SIZE}, AUDITION_MAPPER);
        // TODO: pasar a funcional
        List<Audition> list = new LinkedList<>();
        for(Audition.AuditionBuilder auditionBuilder : auditionsBuilders) {
            list.add(auditionBuilder.build());
        }
        return list;
    }

    @Override
    public List<Audition> getBandAuditions(long userId, int page) {
        StringBuilder sb = new StringBuilder(GET_FULL_AUD_QUERY).append(" WHERE auditions.bandId = ? AND auditions.id IN (SELECT id FROM auditions WHERE bandID = ? LIMIT ? OFFSET ?)" +
                " ORDER BY creationdate DESC, title ASC");
        final String query = sb.toString();
        List<Audition.AuditionBuilder> auditionsBuilders = jdbcTemplate.query(query,new Object[] { userId, userId, PAGE_SIZE,(page -1) * PAGE_SIZE }, AUDITION_MAPPER);
        // TODO: pasar a funcional
        List<Audition> list = new LinkedList<>();
        for(Audition.AuditionBuilder auditionBuilder : auditionsBuilders) {
            list.add(auditionBuilder.build());
        }
        return list;
    }

    @Override
    public List<Audition> search(int page, String query) {
        StringBuilder sb = new StringBuilder(GET_FULL_AUD_QUERY).append(" WHERE LOWER(title) LIKE ?" +
                " AND auditions.id IN (SELECT id FROM auditions LIMIT ? OFFSET ?)" +
                " ORDER BY creationdate DESC, title ASC");
        final String sqlQuery = sb.toString();
        List<Audition.AuditionBuilder> auditionsBuilders = jdbcTemplate.query(sqlQuery,new Object[] { "%" + query.replace("%", "\\%").replace("_", "\\_").toLowerCase() + "%", PAGE_SIZE, (page -1) * PAGE_SIZE}, AUDITION_MAPPER);
        // TODO: pasar a funcional
        List<Audition> list = new LinkedList<>();
        for(Audition.AuditionBuilder auditionBuilder : auditionsBuilders) {
            list.add(auditionBuilder.build());
        }
        return list;
    }

    @Override
    public int getTotalPages(String query) {
        Optional<Integer> result;
        if(query == null || query.isEmpty())
            result = jdbcTemplate.query("SELECT COUNT(*) FROM auditions", TOTAL_AUDITION_ROW_MAPPER).stream().findFirst();
        else
            result = jdbcTemplate.query("SELECT COUNT(*) FROM auditions WHERE title LIKE ?", new Object[] {"%" + query + "%"}, TOTAL_AUDITION_ROW_MAPPER).stream().findFirst();
        //TODO Math.ceil casteado a int puede castear un double muy grande y generar una excepcion
        //TODO tamaño int es la maxima page
        return result.map(integer -> (int) Math.ceil(integer.doubleValue() / PAGE_SIZE)).orElse(0);
    }

    @Override
    public int getTotalBandAuditionPages(long userId) {
        Optional<Integer> result = jdbcTemplate.query("SELECT COUNT(*) FROM auditions WHERE bandId = ?", new Object[] {userId} ,TOTAL_AUDITION_ROW_MAPPER).stream().findFirst();
        return result.map(integer -> (int) Math.ceil(integer.doubleValue() / PAGE_SIZE)).orElse(0);
    }

    @Override
    public void deleteAuditionById(long id) {
        jdbcTemplate.update("DELETE FROM auditions WHERE id = ?", id);
    }

    @Override
    public void editAuditionById(Audition.AuditionBuilder builder, long id) {
        jdbcTemplate.update("UPDATE auditions SET title = ?, description = ?, locationid = ? WHERE id = ?",
                new Object[]{builder.getTitle(), builder.getDescription(), builder.getLocation().getId(), id});

        jdbcTemplate.update("DELETE FROM auditiongenres WHERE auditionid = ?", id);
        jdbcTemplate.update("DELETE FROM auditionroles WHERE auditionid = ?", id);

        roleDao.createAuditionRole(builder.getLookingFor(), id);
        genreDao.createAuditionGenre(builder.getMusicGenres(), id);
    }

    @Override
    public long getMaxAuditionId() {
        return jdbcTemplate.query("SELECT max(id) FROM auditions", (rs,i) -> rs.getLong("max") ).stream().findFirst().orElse(0L);
    }

}
