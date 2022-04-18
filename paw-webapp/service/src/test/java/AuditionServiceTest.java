//import ar.edu.itba.paw.model.Genre;
//import ar.edu.itba.paw.model.Location;
//import ar.edu.itba.paw.model.Role;
//import ar.edu.itba.paw.persistence.Audition;
//import ar.edu.itba.paw.persistence.AuditionDao;
//import ar.edu.itba.paw.service.AuditionServiceImpl;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@RunWith(MockitoJUnitRunner.class)
//public class AuditionServiceTest {
//
//    private static final String TITLE = "TestTitle";
//    private static final String DESCRIPTION = "TestDescription";
//    private static final String EMAIL = "TestEmail@mail.com";
//    private static final long BANDID = 1;
//    private static final LocalDateTime LOCALTIME = LocalDateTime.now();
//    private static final long ID = 1;
//    private static final Location LOCATION = new Location(1,"TESTLOCATION");
//    private static final Genre GENRE = new Genre(1, "TESTGENRE");
//    private static final Role ROLE = new Role(1, "TESTROLE");
//
//    @Mock
//    private AuditionDao mockAuditionDao;
//
//    @InjectMocks
//    private AuditionServiceImpl auditionService = new AuditionServiceImpl(mockAuditionDao);
//
//    @Test
//    public void testCreate() {
//
//        List<Genre> genreList = new ArrayList<>();
//        genreList.add(GENRE);
//        List<Role> roleList = new ArrayList<>();
//        roleList.add(ROLE);
//        Audition.AuditionBuilder builder = new Audition.AuditionBuilder(TITLE, DESCRIPTION, EMAIL, BANDID, LOCALTIME).id(ID).location(LOCATION).lookingFor(roleList).musicGenres(genreList);
//        Mockito.when(mockAuditionDao.create(Mockito.eq(builder))).thenReturn(new Audition(builder.getId(), builder.getBandId(), builder.getTitle(), builder.getDescription(), builder.getEmail(), builder.getCreationDate(), builder.getLocation(), builder.getMusicGenres(), builder.getLookingFor()));
//
//        Optional<Audition> audition = Optional.ofNullable(auditionService.create(builder));
//
//
//        Assert.assertNotNull(audition);
//        Assert.assertTrue(audition.isPresent());
//        Assert.assertEquals(TITLE, audition.get().getTitle());
//    }
//
//
////    @Test
////    public void testCreateEmptyPassword() {
////// 1. Setup!
////// 2. "ejercito" la class under test
////        Optional<User> maybeUser
////                = userService.create(USERNAME, "");
////        // 3. Asserts!
////        Assert.assertNotNull(maybeUser);
////        Assert.assertFalse(maybeUser.isPresent());
////    }
////    @Test
////    public void testCreateAlreadyExists() {
////// 1. Setup!
////        Mockito.when(mockDao.findByUsername(Mockito.eq(USE
////                        RNAME)))
////                .thenReturn(Optional.of(new User(1, USERNAME,
////                        PASSWORD)));
////// 2. "ejercito" la class under test
////        Optional<User> maybeUser
////                = userService.create(USERNAME, PASSWORD);
////// 3. Asserts!
////        Assert.assertNotNull(maybeUser);
////        Assert.assertFalse(maybeUser.isPresent());
////    }
////}
//
//}
