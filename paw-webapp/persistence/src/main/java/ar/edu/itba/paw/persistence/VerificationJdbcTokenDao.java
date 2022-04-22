package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.VerifiactionToken;
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

    private final static RowMapper<VerifiactionToken> VERIFICATION_TOKEN_ROW_MAPPER = (rs, rowNum) -> new VerifiactionToken(rs.getLong("tokenId"),
            rs.getString("token"),
            rs.getLong("userId"),
            rs.getTimestamp("expiryDate").toLocalDateTime());

    @Autowired
    public VerificationJdbcTokenDao(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.simpleJdbcInsert = new SimpleJdbcInsert(ds).withTableName("verificationTokens").usingGeneratedKeyColumns("tokenId");
    }

    @Override
    public Optional<VerifiactionToken> getToken(long id) {
        return jdbcTemplate.query("SELECT * FROM verificationTokens WHERE tokenId = ?",
                new Object[]{id},
               VERIFICATION_TOKEN_ROW_MAPPER).stream().findFirst();
    }


    @Override
    public VerifiactionToken createToken(long userId, String token, LocalDateTime expiryDate) {
        final Map<String, Object> tokenData = new HashMap<>();
        tokenData.put("userId", userId);
        tokenData.put("token", token);
        tokenData.put("expiryDate", expiryDate);

        final Number tokenId = simpleJdbcInsert.executeAndReturnKey(tokenData);
        return  new VerifiactionToken(tokenId.longValue(), token, userId, expiryDate);
    }

    @Override
    public void deleteTokenByUserId(long userId) {
        final String query = "DELETE FROM verificationTokens WHERE userId = ?";
        jdbcTemplate.update(query, userId);
    }
}
