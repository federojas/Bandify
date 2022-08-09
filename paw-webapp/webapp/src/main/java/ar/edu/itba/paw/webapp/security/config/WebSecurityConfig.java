package ar.edu.itba.paw.webapp.security.config;


import ar.edu.itba.paw.webapp.security.filters.JwtFilter;
import ar.edu.itba.paw.webapp.security.services.BandifyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@ComponentScan({"ar.edu.itba.paw.webapp.security.services",
                "ar.edu.itba.paw.webapp.security.filters"} )
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final BandifyUserDetailsService userDetailsService;
    private final JwtFilter jwtFilter;
    @Autowired
    public WebSecurityConfig(final BandifyUserDetailsService userDetailsService,
                             final JwtFilter jwtFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtFilter = jwtFilter;
    }

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

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().headers()
                .cacheControl().disable()
                .and().authorizeRequests()

                // ---------------------- UserController ---------------------------
                .antMatchers(HttpMethod.POST,
                        "/users").anonymous()
                .antMatchers(HttpMethod.GET,
                        "/users",
                        "/users/{\\d+}",
                        "/users/{\\d+}/profile-image",
                        "/users/{\\d+}/applications",
                        "/users/{\\d+}/social-media",
                        "/users/{\\d+}/social-media/{\\d+}").hasAnyRole()
                .antMatchers(HttpMethod.PUT,
                        "/users").hasAnyRole()
                // -------------------------------------------------------------------


                // ----------------------- RoleController -----------------------------


                // --------------------------------------------------------------------
                .antMatchers("/**").permitAll()


                .and().addFilterBefore(jwtFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling();
//                .authenticationEntryPoint(TODO ENTRY POINT)
//                .accessDeniedHandler(TODO AUTH DENIED HANDLER);
    }




    @Override
    public void configure(final WebSecurity web) throws Exception {
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
        //cors.addAllowedOrigin("http://localhost:9000/");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cors);
        return source;
    }

}
