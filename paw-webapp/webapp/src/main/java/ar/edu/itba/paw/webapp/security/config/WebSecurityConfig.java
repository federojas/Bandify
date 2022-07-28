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


    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().headers().cacheControl().disable()
                .and().authorizeRequests()
//                    .antMatchers( "/welcome","/register","/registerBand","/registerArtist","/verify",
//                                 "/resetPassword","/aboutUs","/newPassword","/login","/emailSent","/resetEmailSent").anonymous()
//                    .antMatchers("/apply", "/profile/applications","/editArtist","/success", "/invites", "/invites/{\\d+}", "/profile/bands").hasRole("ARTIST")
//                    .antMatchers("/newAudition", "/profile/auditions", "/profile/editAudition/{\\d+}", "/profile/closeAudition/{\\d+}","/editBand",
//                                 "/auditions/{\\d+}/applicants", "/auditions/{\\d+}/applicants/select/{\\d+}", "/profile/newMembership/{\\d+}", "/profile/bandMembers",
//                                 "/profile/editMembership/{\\d+}", "/user/{\\d+}/invite").hasRole("BAND")
//                    .antMatchers("/profile/**","/auditions/{\\d+}","/bandAuditions/{\\d+}", "/users/search", "/users", "/user/{\\d+}/bandMembers", "/user/{\\d+}/bands",
//                            "/profile/deleteMembership/{\\d+}").authenticated()
//                    .antMatchers("/auditions","/auditions/search", "/", "/user/{\\d+}","/user/{\\d+}/profile-image").permitAll()
                      .antMatchers("/**").permitAll()
                .and().exceptionHandling()
                    .accessDeniedPage("/403")
                .and().csrf().disable()
                .addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class);
    }


    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring().antMatchers( "/css/**", "/js/**", "/images/**", "/icons/**", "/403");
    }


    @Bean
    public CorsConfiguration corsConfiguration() {
        CorsConfiguration cors = new CorsConfiguration();
        //TODO ESTO EN PRODUCCION VUELA !!!!!!!!!!!!!!!!!!!!!!!!
        cors.addAllowedOrigin("http://localhost:9000/");
        return cors;
    }
}
