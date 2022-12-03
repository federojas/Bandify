package ar.edu.itba.paw.webapp.security.filters;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exceptions.UserNotFoundException;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.security.exceptions.UnauthorizedException;
import ar.edu.itba.paw.webapp.security.utils.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;

// TODO: chequear si está bien que esto sea un Component
@Component
public class AuthFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private URL appUrl;

    @Autowired
    private String secretJWT;

    @Autowired
    public AuthFilter(final AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String receivedHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        if(receivedHeader == null || receivedHeader.isEmpty() ||
                !(receivedHeader.startsWith("Bearer ") || receivedHeader.startsWith("Basic "))) {
            throw new InsufficientAuthenticationException("Empty authorization credentials.");
        }

        String[] payload = receivedHeader.split(" ");
        String token;
        if(payload[1] == null)
            throw new InsufficientAuthenticationException("Empty authorization credentials.");
        else
            token = payload[1].trim();
        Authentication auth;

        if (receivedHeader.startsWith("Basic "))
            auth = useBasicAuthentication(token, httpServletResponse);
        else
            auth = useBearerAuthentication(token, httpServletResponse);

        SecurityContextHolder
                .getContext()
                .setAuthentication(auth);
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    private Authentication useBasicAuthentication(String payload,
                                                  HttpServletResponse httpServletResponse) throws AuthenticationException {
        String[] decodedCredentials;
        decodedCredentials = new String(Base64.getDecoder().decode(payload), StandardCharsets.UTF_8).split(":");
        if (decodedCredentials.length != 2) {
            throw new AuthenticationCredentialsNotFoundException("Invalid credentials for basic authorization.");
        }
        final String email = decodedCredentials[0];
        final String password = decodedCredentials[1];

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        final User user = userService.findByEmail(auth.getName()).orElseThrow(UserNotFoundException::new);

        httpServletResponse.addHeader(JwtUtil.JWT_RESPONSE, JwtUtil.generateToken(user, appUrl, secretJWT));
        httpServletResponse.addHeader(JwtUtil.JWT_REFRESH_RESPONSE, userService.getAuthRefreshToken(user.getEmail()).getToken());

        return auth;
    }

    private Authentication useBearerAuthentication(final String payload,
                                                   final HttpServletResponse httpServletResponse) throws AuthenticationException {

        UserDetails userDetails;
        try {
            userDetails = JwtUtil.validateToken(payload, secretJWT);
        } catch(MalformedJwtException  e) {

            final User user = userService.getUserByRefreshToken(payload);

            if (user == null) {
                throw new AuthenticationCredentialsNotFoundException("Invalid JWT or refresh token.");
            }

            httpServletResponse.addHeader(JwtUtil.JWT_RESPONSE, JwtUtil.generateToken(user, appUrl, secretJWT));
            httpServletResponse.addHeader(JwtUtil.JWT_REFRESH_RESPONSE, userService.getAuthRefreshToken(user.getEmail()).getToken());

            final Collection<GrantedAuthority> authorities = new ArrayList<>();
            if (user.isBand())
                authorities.add(new SimpleGrantedAuthority("ROLE_BAND"));
            else
                authorities.add(new SimpleGrantedAuthority("ROLE_ARTIST"));

            return new UsernamePasswordAuthenticationToken
                    (user.getEmail(), user.getPassword(), authorities);
        } catch (ExpiredJwtException | UnsupportedJwtException | SignatureException | IllegalArgumentException e) {
            throw new UnauthorizedException(e.getMessage(), e); //TODO COMO AGARRAMOS
        }

        return new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );
    }
}
