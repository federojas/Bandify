package ar.edu.itba.paw.webapp.security.utils;

import ar.edu.itba.paw.model.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class JwtUtil {

    private static final int REFRESH_RATE_MILLIS = 20 * 60 * 1000; //TODO poner 20 min
    public static final String JWT_RESPONSE = "X-JWT";
    public static final String JWT_REFRESH_RESPONSE = "X-Refresh-Token";

    private JwtUtil() {
    }

    public static UserDetails validateToken(String token, String secretJWT) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
            final Claims claims = extractAllClaims(token, secretJWT);
            final String username = claims.getSubject();
            final Collection<GrantedAuthority> authorities =
                    getAuthorities(claims.get("roles", String.class));
            return new org.springframework.security.core.userdetails.User(username, "", authorities);
    }

    public static String generateToken(User user, URL appUrl, String secretJWT) {
        Map<String, Object> claims = new HashMap<>();
        if(user.isBand())
            claims.put("roles", "BAND");
        else
            claims.put("roles", "ARTIST");
        claims.put("userUrl", appUrl + "users/" + user.getId());
        //TODO ver si es necesario poner issuer audience, etc
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_RATE_MILLIS))
                .signWith(SignatureAlgorithm.HS256, secretJWT)
                .compact();
    }



    private static Claims extractAllClaims(String token, String secretJWT) {
        return Jwts.parser().setSigningKey(secretJWT).parseClaimsJws(token).getBody();
    }

    private static Collection<GrantedAuthority> getAuthorities(String roles) {
        return Arrays.stream(roles.split(" "))
                .map((role) -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }
}