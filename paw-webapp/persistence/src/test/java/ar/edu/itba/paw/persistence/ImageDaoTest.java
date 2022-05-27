package ar.edu.itba.paw.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:profileImageDaoTest.sql")
@Rollback
@Transactional
public class ImageDaoTest {
/*
    @Autowired
    private ImageJdbcDao imageDao;
    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageDaoTest.class);


    private static final long USER_ID_1 = 1;

    private static final File IMAGE = new File("src/test/resources/test.png");
    private static final File IMAGE_2 = new File("src/test/resources/test2.jpg");

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
            imageInfo.put("userid", USER_ID_1);
            imageInfo.put("image", image);
            simpleJdbcInsert.execute(imageInfo);

            final Optional<byte[]> resultImage = imageDao.getProfilePicture(USER_ID_1);
            assertNotNull(resultImage);
            assertTrue(resultImage.isPresent());
            assertArrayEquals(image, resultImage.get());
        } catch (IOException e) {
            LOGGER.warn("Get profile picture test threw io exception");
        }
    }

    @Test
    public void updateProfilePictureCreate()  {
        try {
            final byte[] image = Files.readAllBytes(IMAGE.toPath());
            final byte[] insertedImage = imageDao.updateProfilePicture(USER_ID_1, image);

            assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "profileimages", "userid = " + USER_ID_1));
            assertEquals(image, insertedImage);
        } catch (IOException e) {
            LOGGER.warn("Create profile picture test threw io exception");
        }
    }

    @Test
    public void updateProfilePictureUpdate()  {
        try {
            final byte[] image = Files.readAllBytes(IMAGE.toPath());
            final Map<String, Object> imageInfo = new HashMap<>();
            imageInfo.put("userid", USER_ID_1);
            imageInfo.put("image", image);
            simpleJdbcInsert.execute(imageInfo);

            final byte[] newImage = Files.readAllBytes(IMAGE_2.toPath());
            final byte[] insertedImage = imageDao.updateProfilePicture(USER_ID_1, newImage);

            assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "profileimages", "userid = " + USER_ID_1));
            assertEquals(newImage, insertedImage);
        } catch (IOException e) {
            LOGGER.warn("Update profile picture test threw io exception");
        }
    }



 */
}
