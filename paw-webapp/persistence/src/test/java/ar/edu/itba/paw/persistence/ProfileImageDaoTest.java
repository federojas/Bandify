package ar.edu.itba.paw.persistence;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:profileImageDaoTest.sql")
@Rollback
@Transactional
public class ProfileImageDaoTest {

    @Autowired
    private ImageJdbcDao imageDao;
    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileImageDaoTest.class);


    private static final User user = new User.UserBuilder("artist@mail.com","12345678", "name", false, false).surname("surname").description("description").id(1).build();
    private static final File IMAGE = new File("src/test/resources/test.png");
    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        simpleJdbcInsert = new SimpleJdbcInsert(ds).withTableName("profileimages");
    }

    @Test
    public void testGetProfilePicture() {
        try {
            final byte[] image = Files.readAllBytes(IMAGE.toPath());
            final Map<String, Object> imageInfo = new HashMap<>();
            imageInfo.put("userid", user.getId());
            imageInfo.put("image", image);
            simpleJdbcInsert.execute(imageInfo);

            final Optional<byte[]> resultImage = imageDao.getProfilePicture(user.getId());
            assertNotNull(resultImage);
            assertTrue(resultImage.isPresent());
            assertArrayEquals(image, resultImage.get());
        } catch (IOException e) {
            LOGGER.warn("Get profile picture test threw io exception");
        }
    }
}
