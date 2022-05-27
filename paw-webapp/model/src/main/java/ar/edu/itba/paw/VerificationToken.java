package ar.edu.itba.paw;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "verificationtokens")
public class VerificationToken {

    private static final int EXPIRATION_DAYS = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "verificationtokens_tokenid_seq")
    @SequenceGenerator(sequenceName = "verificationtokens_tokenid_seq", name = "verificationtokens_tokenid_seq", allocationSize = 1)
    @Column(name = "tokenId")
    private long id;

    @Column(name = "token", nullable = false)
    private String token;

    // TODO: EAGER O LAZY?
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @Column(name = "expiryDate", nullable = false)
    private LocalDateTime expiryDate;

    @Enumerated(EnumType.STRING)
    private TokenType type;

    VerificationToken() {
        //Hibernate
    }

    public VerificationToken(String token, User user, LocalDateTime expiryDate, TokenType type) {
        this.token = token;
        this.user = user;
        this.expiryDate = expiryDate;
        this.type = type;
    }

    public static LocalDateTime getNewExpiryDate() {
        return LocalDateTime.now().plusDays(EXPIRATION_DAYS);
    }

    public boolean isValid() {
        return LocalDateTime.now().isBefore(expiryDate);
    }

    public long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public User getUser() {
        return user;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VerificationToken that = (VerificationToken) o;
        return id == that.id && Objects.equals(token, that.token) && Objects.equals(user, that.user) && Objects.equals(expiryDate, that.expiryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, token, user, expiryDate);
    }
}
