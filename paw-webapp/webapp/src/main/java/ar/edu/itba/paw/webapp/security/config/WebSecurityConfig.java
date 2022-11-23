package ar.edu.itba.paw.webapp.security.config;


import ar.edu.itba.paw.webapp.security.BandifyAuthenticationEntryPoint;
import ar.edu.itba.paw.webapp.security.filters.AuthFilter;
import ar.edu.itba.paw.webapp.security.hanlders.BandifyAccessDeniedHandler;
import ar.edu.itba.paw.webapp.security.services.BandifyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@ComponentScan({"ar.edu.itba.paw.webapp.security.services",
                "ar.edu.itba.paw.webapp.security.filters" } )
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private BandifyUserDetailsService userDetailsService;

    @Autowired
    private AuthFilter authFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new BandifyAccessDeniedHandler();
    }

    @Bean
    public BandifyAuthenticationEntryPoint authenticationEntryPoint() {
        return new BandifyAuthenticationEntryPoint();
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().headers()
                .cacheControl().disable()
                .and().authorizeRequests()

//                // ---------------------- UserController ---------------------------
//                .antMatchers(HttpMethod.POST,
//                        "/users").anonymous()
//                .antMatchers(HttpMethod.GET,
//                        "/users",
//                        "/users/{\\d+}",
//                        "/users/{\\d+}/profile-image",
//                        "/users/{\\d+}/applications",
//                        "/users/{\\d+}/social-media",
//                        "/users/{\\d+}/social-media/{\\d+}").hasAnyRole()
//                .antMatchers(HttpMethod.PUT,
//                        "/users").hasAnyRole()
//                // -------------------------------------------------------------------
//
//
//                // ----------------------- RoleController -----------------------------
//
//
//                // --------------------------------------------------------------------
                .antMatchers("/**").authenticated()


                .and().addFilterBefore(authFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                    .authenticationEntryPoint(authenticationEntryPoint()) //TODO CHECK, COMPONENT?
                    .accessDeniedHandler(accessDeniedHandler()); //TODO CHECK
    }



    @Override
    public void configure(final WebSecurity web) {
        web.ignoring().antMatchers( "/css/**", "/js/**", "/images/**", "/icons/**");
    }


    //TODO ESTO EN PRODUCCION VUELA !!!!!!!!!!!!!!!!!!!!!!!!
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cors = new CorsConfiguration();
        cors.setAllowedOrigins(Collections.singletonList("*"));
        cors.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        cors.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        cors.setExposedHeaders(Arrays.asList("x-auth-token", "authorization", "X-Total-Pages",
                "Content-Disposition"));
        //TODO ESTO EN PRODUCCION VUELA !!!!!!!!!!!!!!!!!!!!!!!!
        //cors.addAllowedOrigin("http://localhost:9000/");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cors);
        return source;
    }

}
