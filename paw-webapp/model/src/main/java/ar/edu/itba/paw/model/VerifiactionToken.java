package ar.edu.itba.paw.model;

import java.time.LocalDateTime;

public class VerifiactionToken {


    private long id;
    private String token;
    private long userId;
    private LocalDateTime expiryDate;

    public VerifiactionToken(long id, String token, long userId, LocalDateTime expiryDate) {
        this.id = id;
        this.token = token;
        this.userId = userId;
        this.expiryDate = expiryDate;
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

    public void setId(long id) {
        this.id = id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }
}
