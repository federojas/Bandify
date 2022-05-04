//
//package ar.edu.itba.paw.persistence;
//
//import static org.junit.Assert.*;
//import ar.edu.itba.paw.persistence.Genre;
//import ar.edu.itba.paw.persistence.Location;
//import ar.edu.itba.paw.persistence.Role;
//import org.junit.*;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.jdbc.JdbcTestUtils;
//
//import javax.sql.DataSource;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = TestConfig.class)
//@Sql("classpath:test.sql")
//public class AuditionJdbcDaoTest {
//
//    private static final String TITLE = "TestTitle";
//    private static final String DESCRIPTION = "TestDescription";
//    private static final String EMAIL = "TestEmail@mail.com";
//    private static final long BANDID = 1;
//    private static final LocalDateTime LOCALTIME = LocalDateTime.now();
//    private static final long ID = 1;
//    private static final long ID2 = 2;
//    private static final long INVALIDID = 3;
//    private static final Location LOCATION = new Location(1,"TESTLOCATION");
//    private static final Genre GENRE = new Genre(1, "TESTGENRE");
//    private static final Role ROLE = new Role(1, "TESTROLE");
//    private static final int PAGE = 1;
//
//    @Autowired
//    private DataSource dataSource;
//
//    @Autowired
//    private AuditionJdbcDao auditionJdbcDao;
//
//    private JdbcTemplate jdbcTemplate;
//
//    @Before
//    public void setUp() {
//        jdbcTemplate = new JdbcTemplate(dataSource);
//        JdbcTestUtils.deleteFromTables(jdbcTemplate, "auditions");
//        JdbcTestUtils.deleteFromTables(jdbcTemplate, "locations");
//        JdbcTestUtils.deleteFromTables(jdbcTemplate, "genres");
//        JdbcTestUtils.deleteFromTables(jdbcTemplate, "roles");
//    }
//
//    @Test
//    public void testCreate() {
//        List<Genre> genreList = new ArrayList<>();
//        genreList.add(GENRE);
//        List<Role> roleList = new ArrayList<>();
//        roleList.add(ROLE);
//
//        Audition.AuditionBuilder builder = new Audition.AuditionBuilder(TITLE, DESCRIPTION, EMAIL, BANDID, LOCALTIME).id(ID).location(LOCATION).musicGenres(genreList).lookingFor(roleList);
//
//        final Audition audition = auditionJdbcDao.create(builder);
//        //final Audition audition2 = auditionJdbcDao.create(new Audition.AuditionBuilder(TITLE, DESCRIPTION, EMAIL, BANDID, LOCALTIME).id(ID2).location(LOCATION).musicGenres(genreList).lookingFor(roleList));
//        assertNotNull(audition);
//        //assertNotNull(audition2);
//    }
//
//    @Test
//    public void testFindById() {
//        final Optional<Audition> audition = auditionJdbcDao.getAuditionById(ID);
//
//        assertNotNull(audition);
//        assertTrue(audition.isPresent());
//        assertEquals(ID, audition.get().getId());
//    }
//
//    @Test
//    public void testFindByIdInvalid() {
//
//        final Optional<Audition> audition = auditionJdbcDao.getAuditionById(INVALIDID);
//
//        assertNotNull(audition);
//        assertFalse(audition.isPresent());
//    }
//
//    @Test
//    public void testGetAll() {
//
//        final List<Audition> auditions = auditionJdbcDao.getAll(PAGE);
//
//        assertEquals(1, auditions.size());
//    }
//}
