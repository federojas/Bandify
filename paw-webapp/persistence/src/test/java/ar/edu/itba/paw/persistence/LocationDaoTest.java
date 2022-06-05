package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Location;
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

    @Autowired
    private LocationJpaDao locationDao;

    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;

    private static final Location location1 = new Location(1L, "loc");
    private static final Location location2 = new Location(2L, "loc2");
    private static final Location location3 = new Location(3L, "loc3");

    private static final List<Location> LOCATIONS = Arrays.asList(location1, location2, location3);
    private static final List<String> LOCATION_NAMES = Arrays.asList("loc", "loc2", "loc3");

    private static final String INVALID_NAME = "INVALIDO";

    private static final String LOCATION_NAME = "loc";
    private static final long LOCATION_ID = 1;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void testGetAll() {
        final List<Location> locations = locationDao.getAll();

        assertNotNull(locations);
        assertEquals(3, locations.size());
        assertTrue(LOCATIONS.containsAll(locations));
        assertEquals(LOCATIONS.size(), locations.size());
        assertEquals(JdbcTestUtils.countRowsInTable(jdbcTemplate, "locations"), locations.size());
    }

    @Test
    public void testGetLocationByName() {
        final Optional<Location> optionalLocation = locationDao.getLocationByName(LOCATION_NAME);
        assertNotNull(optionalLocation);
        assertTrue(optionalLocation.isPresent());
        assertEquals(location1, optionalLocation.get());
    }

    @Test
    public void testGetLocationByInvalidName() {
        final Optional<Location> optionalLocation = locationDao.getLocationByName(INVALID_NAME);
        assertNotNull(optionalLocation);
        assertFalse(optionalLocation.isPresent());
    }

    @Test
    public void testGetLocationByNames() {
        int i = 0;
        final Set<Location> locations = locationDao.getLocationsByNames(LOCATION_NAMES);

        assertNotNull(locations);
        assertEquals(3, locations.size());
        assertEquals(LOCATIONS.size(), locations.size());
        assertTrue(LOCATIONS.containsAll(locations));
    }

    @Test
    public void testGetLocationByInvalidNames() {
        final Set<Location> locations = locationDao.getLocationsByNames(Collections.singletonList(INVALID_NAME));
        assertNotNull(locations);
        assertTrue(locations.isEmpty());
    }
}
