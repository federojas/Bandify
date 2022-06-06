package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.TokenType;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.VerificationToken;
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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:verificationTokenDaoTest.sql")
@Transactional
@Rollback
public class VerificationTokenDaoTest {

    @Autowired
    private VerificationTokenDao verificationTokenDao;
    @Autowired
    private DataSource ds;
    @PersistenceContext
    private EntityManager em;

    private JdbcTemplate jdbcTemplate;

    private static final long USER_ID = 1;
    private static final User USER = new User.UserBuilder("email@email.com","secret","name",true,false).id(USER_ID).build();
    private static final long INVALID_ID = 20;
    private static final String TOKEN = "c73bf3f5-8d3e-4ddd-8105-9acd870dc0ab";
    private static final String INVALID_TOKEN = "INVALIDO";
    private static final LocalDateTime EXPIRY_DATE = LocalDateTime.now().plusDays(1);
    private static final LocalDateTime USER_VERIFY_TOKEN_EXPIRY_DATE = LocalDateTime.of(2022 ,7, 5, 14, 23, 30);
    private static final String USER_VERIFY_TOKEN = "token-verify";

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void testCreateToken() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"verificationtokens");
        VerificationToken token1 = verificationTokenDao.createToken(USER, TOKEN, EXPIRY_DATE, TokenType.VERIFY);
        em.flush();
        assertNotNull(token1);
        assertEquals(USER, token1.getUser());
        assertEquals(TOKEN, token1.getToken());
        assertEquals(EXPIRY_DATE, token1.getExpiryDate());
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "verificationtokens", "tokenid = " + token1.getId()));
    }

    @Test
    public void testDeleteTokenByUserId() {
        verificationTokenDao.deleteTokenByUserId(USER_ID, TokenType.RESET);
        em.flush();
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "verificationtokens", "userid = " + USER_ID));
    }

    @Test
    public void testDeleteTokenByInvalidUserId() {
        verificationTokenDao.deleteTokenByUserId(INVALID_ID, TokenType.RESET);
        em.flush();
        assertEquals(3, JdbcTestUtils.countRowsInTable(jdbcTemplate, "verificationtokens"));
    }

    @Test
    public void testDeleteTokenByUserIdInvalidType() {
        verificationTokenDao.deleteTokenByUserId(INVALID_ID, TokenType.VERIFY);
        em.flush();
        assertEquals(3, JdbcTestUtils.countRowsInTable(jdbcTemplate, "verificationtokens"));
    }

    @Test
    public void testGetToken() {
        Optional<VerificationToken> verificationToken = verificationTokenDao.getToken(USER_VERIFY_TOKEN);
        assertNotNull(verificationToken);
        assertTrue(verificationToken.isPresent());
        assertEquals(2, verificationToken.get().getId());
        assertEquals(USER_VERIFY_TOKEN, verificationToken.get().getToken());
        assertEquals(TokenType.VERIFY, verificationToken.get().getType());
        assertEquals(verificationToken.get().getExpiryDate(), USER_VERIFY_TOKEN_EXPIRY_DATE);
    }

    @Test
    public void testGetInvalidToken() {
        Optional<VerificationToken> verificationToken = verificationTokenDao.getToken(INVALID_TOKEN);
        assertNotNull(verificationToken);
        assertFalse(verificationToken.isPresent());
    }

}
