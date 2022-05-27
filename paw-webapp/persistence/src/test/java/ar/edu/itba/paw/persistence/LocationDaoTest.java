package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.Location;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:locationDaoTest.sql")
@Rollback
@Transactional
public class LocationDaoTest {
/*
    @Autowired
    private LocationJdbcDao locationDao;
    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;

    private static final Location location = new Location(1,"loc");
    private static final Location location2 = new Location(2,"loc2");
    private static final Location location3 = new Location(3,"loc3");
    private static final List<Location> ALL_LOCATIONS = Arrays.asList(location, location2, location3);

    private static final long INVALID_ID = 0;
    private static final long AUDITION_ID = 1;
    private static final String INVALID_NAME = "INVALIDO";

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void testGetAll() {
        final List<Location> locations = locationDao.getAll();
        assertTrue(ALL_LOCATIONS.containsAll(locations));
        assertEquals(JdbcTestUtils.countRowsInTable(jdbcTemplate, "locations"), locations.size());
    }

    @Test
    public void testGetLocationByName() {
        final Optional<Location> optionalLocation = locationDao.getLocationByName(location.getName());
        assertNotNull(optionalLocation);
        assertTrue(optionalLocation.isPresent());
        assertEquals(location, optionalLocation.get());
    }

    @Test
    public void testGetLocationByInvalidName() {
        final Optional<Location> optionalLocation = locationDao.getLocationByName(INVALID_NAME);
        assertNotNull(optionalLocation);
        assertFalse(optionalLocation.isPresent());
    }

 */
}
