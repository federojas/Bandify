package ar.edu.itba.paw.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class ImageJdbcDao implements ImageDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageJdbcDao.class);

    private final static RowMapper<byte[]> IMAGE_ROW_MAPPER = (rs, i) -> rs.getBytes("image");

    @Autowired
    public ImageJdbcDao(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("profileimages");
    }

    @Override
    public byte[] updateProfilePicture(long userId, byte[] image) {
        if(hasProfilePicture(userId))
            jdbcTemplate.update("UPDATE profileimages SET image = ? WHERE userid = ?", image, userId);
        else
            crateProfilePicture(userId, image);
        LOGGER.info("New profile picture for user {}", userId);
        return image;
    }

    private boolean hasProfilePicture(long userId) {
        return jdbcTemplate.queryForObject("SELECT EXISTS(SELECT * FROM profileimages WHERE userid = ?)", new Object[] {userId}, Boolean.class);
    }

    private void crateProfilePicture(long userId, byte[] image) {
        final Map<String, Object> imageData = new HashMap<>();
        imageData.put("userid", userId);
        imageData.put("image", image);
        jdbcInsert.execute(imageData);
    }

    @Override
    public Optional<byte[]> getProfilePicture(long userId) {
        return jdbcTemplate.query("SELECT * FROM profileImages WHERE userId = ?", new Object[] { userId }, IMAGE_ROW_MAPPER).stream().findFirst();
    }
}
