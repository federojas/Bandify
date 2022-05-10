package ar.edu.itba.paw.persistence;

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


import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:verificationTokenDaoTest.sql")
@Rollback
@Transactional
public class VerificationTokenDaoTest {

    @Autowired
    private VerificationJdbcTokenDao verificationTokenDao;
    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;

    private static final long USER_ID = 1;
    private static final long OTHER_USER_ID = 2;
    private static final long INVALID_ID = 20;

    private static final String TOKEN = "token";
    private static final String INVALID_TOKEN = "INVALIDO";

    private static final LocalDateTime EXPIRY_DATE = LocalDateTime.now().plusDays(1);

    private static final LocalDateTime USER_VERIFY_TOKEN_EXPIRY_DATE = LocalDateTime.of(2022 ,7, 5, 14, 23, 30);
    private static final String USER_VERIFY_TOKEN = "token-verify";

    private static final VerificationToken USER_TOKEN = new VerificationToken(2,USER_VERIFY_TOKEN,USER_ID,USER_VERIFY_TOKEN_EXPIRY_DATE);
    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void testCreateToken() {
        VerificationToken token1 = verificationTokenDao.createToken(OTHER_USER_ID, TOKEN, EXPIRY_DATE, TokenType.VERIFY);
        assertNotNull(token1);
        assertEquals(OTHER_USER_ID, token1.getUserId());
        assertEquals(TOKEN, token1.getToken());
        assertEquals(EXPIRY_DATE, token1.getExpiryDate());
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "verificationtokens", "tokenid = " + token1.getId()));
    }

    @Test
    public void testDeleteTokenByUserId() {
        verificationTokenDao.deleteTokenByUserId(USER_ID, TokenType.RESET);
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "verificationtokens", "userid = " + USER_ID));
    }

    @Test
    public void testDeleteTokenByInvalidUserId() {
        verificationTokenDao.deleteTokenByUserId(INVALID_ID, TokenType.RESET);
        assertEquals(3, JdbcTestUtils.countRowsInTable(jdbcTemplate, "verificationtokens"));
    }

    @Test
    public void testDeleteTokenByUserIdInvalidType() {
        verificationTokenDao.deleteTokenByUserId(INVALID_ID, TokenType.VERIFY);
        assertEquals(3, JdbcTestUtils.countRowsInTable(jdbcTemplate, "verificationtokens"));
    }

    @Test
    public void testGetToken() {
        Optional<VerificationToken> verificationToken = verificationTokenDao.getToken(USER_VERIFY_TOKEN);
        assertNotNull(verificationToken);
        assertTrue(verificationToken.isPresent());
        assertEquals(USER_TOKEN, verificationToken.get());
    }

    @Test
    public void testGetInvalidToken() {
        Optional<VerificationToken> verificationToken = verificationTokenDao.getToken(INVALID_TOKEN);
        assertNotNull(verificationToken);
        assertFalse(verificationToken.isPresent());
    }
}
