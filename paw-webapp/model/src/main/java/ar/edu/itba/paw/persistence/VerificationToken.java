package ar.edu.itba.paw.persistence;

import java.time.LocalDateTime;

public class VerificationToken {

    private static final int EXPIRATION_DAYS = 1;

    private long id;
    private String token;
    private long userId;
    private LocalDateTime expiryDate;

    protected VerificationToken(long id, String token, long userId, LocalDateTime expiryDate) {
        this.id = id;
        this.token = token;
        this.userId = userId;
        this.expiryDate = expiryDate;
    }

    public static LocalDateTime getNewExpiryDate() {
        return LocalDateTime.now().plusDays(EXPIRATION_DAYS);
    }

    public boolean isValid() {
        return LocalDateTime.now().isAfter(expiryDate);
    }

    public long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public long getUserId() {
        return userId;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

}
