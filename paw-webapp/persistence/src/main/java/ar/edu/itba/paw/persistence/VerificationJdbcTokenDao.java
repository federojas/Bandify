package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.TokenType;
import ar.edu.itba.paw.model.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class VerificationJdbcTokenDao implements VerificationTokenDao {

    private JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private final static RowMapper<VerificationToken> VERIFICATION_TOKEN_ROW_MAPPER = (rs, rowNum) -> new VerificationToken(rs.getLong("tokenId"),
            rs.getString("token"),
            rs.getLong("userId"),
            rs.getTimestamp("expiryDate").toLocalDateTime());

    @Autowired
    public VerificationJdbcTokenDao(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("verificationtokens").usingGeneratedKeyColumns("tokenid");
    }

    @Override
    public Optional<VerificationToken> getToken(String token) {
        return jdbcTemplate.query("SELECT * FROM verificationTokens WHERE token = ?",
                new Object[]{token},
               VERIFICATION_TOKEN_ROW_MAPPER).stream().findFirst();
    }


    @Override
    public VerificationToken createToken(long userId, String token, LocalDateTime expiryDate, TokenType type) {
        final Map<String, Object> tokenData = new HashMap<>();
        tokenData.put("userId", userId);
        tokenData.put("token", token);
        tokenData.put("expiryDate", expiryDate);
        tokenData.put("type",type.getType());

        final Number tokenId = simpleJdbcInsert.executeAndReturnKey(tokenData);
        return  new VerificationToken(tokenId.longValue(), token, userId, expiryDate);
    }

    @Override
    public void deleteTokenByUserId(long userId, TokenType type) {
        final String query = "DELETE FROM verificationTokens WHERE userId = ? AND type = ?";
        jdbcTemplate.update(query, userId, type.getType());
    }
}
