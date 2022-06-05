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

    private final long[] locationIds = {1L, 2L, 3L};
    private final String[] locationNames = {"loc", "loc2", "loc3"};

    private static final String INVALID_NAME = "INVALIDO";

    private static final String LOCATION_NAME = "loc";
    private static final long LOCATION_ID = 1;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void testGetAll() {
        int i = 0;
        final List<Location> locations = locationDao.getAll();

        assertNotNull(locations);
        assertEquals(3, locations.size());

        for (Location location : locations) {
           assertEquals(locationIds[i], location.getId());
           assertEquals(locationNames[i], location.getName());
           i++;
        }

        assertEquals(JdbcTestUtils.countRowsInTable(jdbcTemplate, "locations"), locations.size());
    }

    @Test
    public void testGetLocationByName() {
        final Optional<Location> optionalLocation = locationDao.getLocationByName(LOCATION_NAME);
        assertNotNull(optionalLocation);
        assertTrue(optionalLocation.isPresent());
        assertEquals(LOCATION_NAME, optionalLocation.get().getName());
        assertEquals(LOCATION_ID, optionalLocation.get().getId());
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
        final Set<Location> locations = locationDao.getLocationsByNames(Arrays.asList(locationNames));

        assertNotNull(locations);
        assertEquals(3, locations.size());

        for (Location location : locations) {
            assertEquals(locationIds[i], location.getId());
            assertEquals(locationNames[i], location.getName());
            i++;
        }
    }

    @Test
    public void testGetLocationByInvalidNames() {
        final Set<Location> locations = locationDao.getLocationsByNames(Collections.singletonList(INVALID_NAME));
        assertNotNull(locations);
        assertTrue(locations.isEmpty());
    }
}
