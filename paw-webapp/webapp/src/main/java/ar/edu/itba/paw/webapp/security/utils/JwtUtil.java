package ar.edu.itba.paw.webapp.security.utils;

import ar.edu.itba.paw.model.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@PropertySource(value= {"classpath:application.properties"})
public class JwtUtil {

    private static final int REFRESH_RATE_MILLIS = 20 * 1000 * 60;
    public static final String JWT_RESPONSE = "X-JWT";
    public static final String JWT_REFRESH_RESPONSE = "X-Refresh-Token";

    private JwtUtil() {
    }

    @Autowired
    private static Environment environment;

    private final static String SECRET_KEY = "secret";

    public static UserDetails validateToken(String token) {
        try {
            final Claims claims = extractAllClaims(token);
            final String username = claims.getSubject();
            final Collection<GrantedAuthority> authorities =
                    getAuthorities(claims.get("roles", String.class));
            return new org.springframework.security.core.userdetails.User(username, "", authorities);

        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            return null;
        }
    }

    public static String generateToken(User user) throws MalformedURLException {
        Map<String, Object> claims = new HashMap<>();
        if(user.isBand())
            claims.put("roles", "BAND");
        else
            claims.put("roles", "ARTIST");
        claims.put("userUrl", /*getAppBaseUrl() + TODO NO ANDA NO SE PORQUE */ "api/users/" + user.getId());
        return "Bearer " + Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_RATE_MILLIS))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    private static URL getAppBaseUrl() throws MalformedURLException {
        return new URL(environment.getRequiredProperty("app.protocol"),
                environment.getRequiredProperty("app.base.url"),
                Integer.parseInt(environment.getRequiredProperty("app.port")),
                environment.getRequiredProperty("app.group.directory"));
    }

    public static String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public static Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private static Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private static Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private static Collection<GrantedAuthority> getAuthorities(String roles) {
        return Arrays.stream(roles.split(" "))
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }
}