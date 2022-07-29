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

import java.util.concurrent.TimeUnit;


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
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.sessionManagement()
                .invalidSessionUrl("/welcome")
                .and().authorizeRequests()
                .antMatchers(HttpMethod.GET, "/users").permitAll()
                .antMatchers(HttpMethod.POST, "/users").permitAll()
                .and().exceptionHandling()
                .and().csrf().disable();
    }


    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring().antMatchers( "/css/**", "/js/**", "/images/**", "/icons/**", "/403");
    }


    /*
    @Bean
    public CorsConfiguration corsConfiguration() {
        CorsConfiguration cors = new CorsConfiguration();
        //TODO ESTO EN PRODUCCION VUELA !!!!!!!!!!!!!!!!!!!!!!!!
        cors.addAllowedOrigin("http://localhost:9000/");
        return cors;
    }
     */
}
