package ar.edu.itba.paw.webapp.security.config;


import ar.edu.itba.paw.webapp.security.services.BandifyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
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
@ComponentScan("ar.edu.itba.paw.webapp.security.services")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private BandifyUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private Environment environment;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    // TODO: nueva configuracion de spring security
//    @Override
//    protected void configure(final HttpSecurity http) throws Exception {
//        http.sessionManagement()
//                .invalidSessionUrl("/welcome")
//                .and().authorizeRequests()
//                .antMatchers(HttpMethod.GET, "/users").permitAll()
//                .antMatchers(HttpMethod.POST, "/users").permitAll()
//                .and().exceptionHandling()
//                .and().csrf().disable();
//    }

    // TODO: nueva configuracion de spring security
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//              .invalidSessionUrl("/welcome") TODO VUELA?
                .and().authorizeRequests()
                .anyRequest().permitAll()
                .and().addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling();
//                .authenticationEntryPoint(TODO ENTRY POINT)
//                .accessDeniedHandler(TODO AUTH DENIED HANDLER);
    }




    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring().antMatchers( "/css/**", "/js/**", "/images/**", "/icons/**");
    }


    //TODO revisar
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cors = new CorsConfiguration();
        cors.setAllowedOrigins(Collections.singletonList("*"));
        cors.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        cors.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        cors.setExposedHeaders(Arrays.asList("x-auth-token", "authorization", "X-Total-Pages", "Content-Disposition"));
        //TODO ESTO EN PRODUCCION VUELA !!!!!!!!!!!!!!!!!!!!!!!!
//        cors.addAllowedOrigin("http://localhost:9000/");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cors);
        return source;
    }

}
