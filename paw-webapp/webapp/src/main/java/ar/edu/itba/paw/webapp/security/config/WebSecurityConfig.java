package ar.edu.itba.paw.webapp.security.config;


import ar.edu.itba.paw.webapp.security.services.BandifyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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


    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.sessionManagement()
                    .invalidSessionUrl("/welcome")
                .and().authorizeRequests()
                    .antMatchers( "/welcome","/register","/registerBand","/registerArtist","/verify",
                                 "/resetPassword","/aboutUs","/newPassword").anonymous()
                    .antMatchers("/apply").hasRole("ARTIST")
                    .antMatchers("/newAudition").hasRole("BAND")
                    .antMatchers("/profile","/","/auditions","/search").authenticated()
                .and().formLogin()
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/", false)
                    .loginPage("/welcome")
                .and().rememberMe()
                    .rememberMeParameter("rememberme")
                    .userDetailsService(userDetailsService)
                    .key(environment.getRequiredProperty("security.rememberme.key"))
                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))
                .and().logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/welcome")
                .and().exceptionHandling()
                    .accessDeniedPage("/403")
                .and().csrf().disable();
    }


    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring().antMatchers( "/*.css", "/*.js", " /favicon.ico", "/manifest.json", "/*.png", "/*.svg");
    }
}
