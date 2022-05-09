
package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.AuditionFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class AuditionJdbcDao implements AuditionDao {

    private final int PAGE_SIZE = 9;

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcAuditionInsert;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final GenreDao genreDao;
    private final RoleDao roleDao;

    private final static RowMapper<Genre> GENRE_ROW_MAPPER = (rs, i) -> new Genre(rs.getLong("genres.id"), rs.getString("genre"));
    private final static RowMapper<Role> ROLE_ROW_MAPPER = (rs, i) -> new Role(rs.getLong("roles.id"), rs.getString("role"));
    private final static RowMapper<Location> LOCATION_ROW_MAPPER = (rs, i) -> new Location(rs.getLong("locations.id"), rs.getString("location"));
    private final static RowMapper<Audition.AuditionBuilder> AUDITION_ROW_MAPPER = (rs, i) -> {
        return new Audition.AuditionBuilder(
                rs.getString("title"),
                rs.getString("description"),
                rs.getLong("bandId"),
                rs.getTimestamp("creationDate").toLocalDateTime()
        ).id(rs.getLong("id"));
    };
    private final static ResultSetExtractor<List<Audition.AuditionBuilder>> AUDITION_MAPPER = rs -> {
        Map<Long, Audition.AuditionBuilder> auditionsById = new LinkedHashMap<>();
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
    private final static RowMapper<Integer> TOTAL_AUDITION_ROW_MAPPER = (rs, i) -> rs.getInt("auditionTotal");

    private final String GET_FULL_AUD_QUERY = "SELECT auditions.id,bandid,title,description,creationdate,location,genre,role,roles.id, genres.id, locations.id" +
            " FROM auditions JOIN auditiongenres ON auditions.id = auditiongenres.auditionid JOIN auditionroles ON auditions.id = auditionroles.auditionid" +
            " JOIN locations ON auditions.locationid = locations.id JOIN genres ON genres.id = auditiongenres.genreid" +
            " JOIN roles ON roles.id = auditionroles.roleid";

    @Autowired
    public AuditionJdbcDao(final DataSource ds, GenreDao genreDao, RoleDao roleDao) {
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.genreDao = genreDao;
        this.roleDao = roleDao;
        this.jdbcAuditionInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("auditions").usingGeneratedKeyColumns("id");
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(ds);
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
        auditionData.put("creationDate", Timestamp.valueOf(builder.getCreationDate()));
        final Number id = jdbcAuditionInsert.executeAndReturnKey(auditionData);

        roleDao.createAuditionRole(builder.getLookingFor(),id.longValue());
        genreDao.createAuditionGenre(builder.getMusicGenres(), id.longValue());

        return builder.id(id.longValue()).build();
    }

    @Override
    public List<Audition> getAll(int page) {
        StringBuilder sb = new StringBuilder(GET_FULL_AUD_QUERY).append(" WHERE auditions.id IN (SELECT id FROM auditions ORDER BY creationdate DESC LIMIT ? OFFSET ?) ORDER BY creationdate DESC");
        final String query = sb.toString();
        List<Audition.AuditionBuilder> auditionsBuilders = jdbcTemplate.query(query,new Object[] { PAGE_SIZE, (page -1) * PAGE_SIZE}, AUDITION_MAPPER);
        return auditionsBuilders.stream().map(Audition.AuditionBuilder::build).collect(Collectors.toList());
    }

    @Override
    public List<Audition> getBandAuditions(long userId, int page) {
        final String query = GET_FULL_AUD_QUERY + " WHERE auditions.id IN (SELECT id FROM auditions WHERE bandId = ? ORDER BY creationdate DESC LIMIT ? OFFSET ?) ORDER BY creationdate DESC";
        List<Audition.AuditionBuilder> auditionsBuilders = jdbcTemplate.query(query,new Object[] { userId, PAGE_SIZE,(page -1) * PAGE_SIZE }, AUDITION_MAPPER);
        return auditionsBuilders.stream().map(Audition.AuditionBuilder::build).collect(Collectors.toList());
    }

    @Override
    public int getTotalPages() {
        Optional<Integer> result = jdbcTemplate.query("SELECT COUNT(*) AS auditionTotal FROM auditions", TOTAL_AUDITION_ROW_MAPPER).stream().findFirst();
        //TODO Math.ceil casteado a int puede castear un double muy grande y generar una excepcion
        //TODO tamaño int es la maxima page
        return result.map(integer -> (int) Math.ceil(integer.doubleValue() / PAGE_SIZE)).orElse(0);
    }

    @Override
    public int getTotalBandAuditionPages(long userId) {
        Optional<Integer> result = jdbcTemplate.query("SELECT COUNT(*) AS auditionTotal FROM auditions WHERE bandId = ?", new Object[] {userId} ,TOTAL_AUDITION_ROW_MAPPER).stream().findFirst();
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
        return jdbcTemplate.query("SELECT MAX(id) AS maxId FROM auditions", (rs,i) -> rs.getLong("maxId") ).stream().findFirst().orElse(0L);
    }

    @Override
    public List<Audition> filter(AuditionFilter filter, int page) {
        MapSqlParameterSource in = parameterSource(filter);
        List<Audition.AuditionBuilder> auditionsBuilders = namedParameterJdbcTemplate.query(
                GET_FULL_AUD_QUERY +
                " WHERE auditions.id IN (" +
                        "SELECT DISTINCT a.aId FROM (" +
                            "SELECT DISTINCT auditions.id as aId, creationdate FROM " +
                            "auditions JOIN auditiongenres ON id = auditiongenres.auditionid JOIN auditionroles ON id = auditionroles.auditionid " +
                            "JOIN locations ON auditions.locationid = locations.id JOIN genres ON genres.id = auditiongenres.genreid " +
                            "JOIN roles ON roles.id = auditionroles.roleid " +
                            "WHERE (COALESCE(:genresNames,null) IS NULL OR genre IN (:genresNames)) AND " +
                            "(COALESCE(:rolesNames,null) IS NULL OR role IN (:rolesNames)) AND " +
                            "(COALESCE(:locations,null) IS NULL OR location IN (:locations)) AND " +
                            "(COALESCE(:title,null) IS NULL OR LOWER(title) LIKE :title ) ORDER BY creationdate " + filter.getOrder() + " LIMIT " + PAGE_SIZE + " OFFSET " + (page -1) * PAGE_SIZE + " ) AS a " +
                        ")" +
                "ORDER BY auditions.creationdate " + filter.getOrder(),in,AUDITION_MAPPER);
        return auditionsBuilders.stream().map(Audition.AuditionBuilder::build).collect(Collectors.toList());
    }

    private MapSqlParameterSource parameterSource(AuditionFilter filter) {
        String title = filter.getTitle().equals("") ? null : "%" + filter.getTitle().replace("%", "\\%").replace("_", "\\_").toLowerCase() + "%";
        return new MapSqlParameterSource()
                .addValue("genresNames", filter.getGenresNames())
                .addValue("rolesNames", filter.getRolesNames())
                .addValue("locations", filter.getLocations())
                .addValue("title", title)
                .addValue("order", filter.getOrder());
    }

    @Override
    public int getTotalPages(AuditionFilter filter) {
        MapSqlParameterSource in = parameterSource(filter);
        String sqlQuery = "SELECT COUNT(DISTINCT auditions.id) AS auditionTotal " +
                "FROM auditions JOIN auditiongenres ON id = auditiongenres.auditionid JOIN auditionroles ON id = auditionroles.auditionid" +
                " JOIN locations ON auditions.locationid = locations.id JOIN genres ON genres.id = auditiongenres.genreid" +
                " JOIN roles ON roles.id = auditionroles.roleid " +
                "WHERE (COALESCE(:genresNames,null) IS NULL OR genre IN (:genresNames)) AND " +
                "(COALESCE(:rolesNames,null) IS NULL OR role IN (:rolesNames)) AND " +
                "(COALESCE(:locations,null) IS NULL OR location IN (:locations)) AND "+
                "(COALESCE(:title,null) IS NULL OR LOWER(title) LIKE :title )";
        Optional<Integer> result = namedParameterJdbcTemplate.query(sqlQuery,in,TOTAL_AUDITION_ROW_MAPPER).stream().findFirst();
        return result.map(integer -> (int) Math.ceil(integer.doubleValue() / PAGE_SIZE)).orElse(0);
    }
}
