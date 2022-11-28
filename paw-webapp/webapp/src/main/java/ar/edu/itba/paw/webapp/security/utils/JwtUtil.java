package ar.edu.itba.paw.webapp.security.utils;

import ar.edu.itba.paw.model.User;
import io.jsonwebtoken.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class JwtUtil {

    private static final int REFRESH_RATE_MILLIS = 1000 * 30; //TODO poner 20 min
    public static final String JWT_RESPONSE = "X-JWT";
    public static final String JWT_REFRESH_RESPONSE = "X-Refresh-Token";

    private JwtUtil() {
    }

    private final static String SECRET_KEY = "secret";

    public static UserDetails validateToken(String token) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
            final Claims claims = extractAllClaims(token);
            final String username = claims.getSubject();
            final Collection<GrantedAuthority> authorities =
                    getAuthorities(claims.get("roles", String.class));
            return new org.springframework.security.core.userdetails.User(username, "", authorities);
    }

    public static String generateToken(User user, URL appUrl) {
        Map<String, Object> claims = new HashMap<>();
        if(user.isBand())
            claims.put("roles", "BAND");
        else
            claims.put("roles", "ARTIST");
        claims.put("userUrl", appUrl + "users/" + user.getId());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_RATE_MILLIS))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }



    private static Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private static Collection<GrantedAuthority> getAuthorities(String roles) {
        return Arrays.stream(roles.split(" "))
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }
}