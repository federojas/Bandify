package ar.edu.itba.paw.webapp.security.config;


import ar.edu.itba.paw.webapp.security.handlers.BandifyAuthenticationEntryPoint;
import ar.edu.itba.paw.webapp.security.filters.AuthFilter;
import ar.edu.itba.paw.webapp.security.handlers.BandifyAccessDeniedHandler;
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
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CharacterEncodingFilter;


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
    @Override
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
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http
                .cors().and().csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new BandifyAuthenticationEntryPoint())
                .accessDeniedHandler(accessDeniedHandler()) //TODO 500 CUANDO PONES MAL LAS CREDENTIALS TOKENS ETC
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().headers()
                .cacheControl().disable()
                .and().authorizeRequests()
//                .antMatchers( "/welcome","/register","/registerBand","/registerArtist","/verify",
//                        "/resetPassword","/aboutUs","/newPassword","/login","/emailSent","/resetEmailSent").anonymous()
//                .antMatchers("/apply", "/profile/applications","/editArtist","/success", "/invites", "/invites/{\\d+}", "/profile/bands").hasRole("ARTIST")
//                .antMatchers("/newAudition", "/profile/auditions", "/profile/editAudition/{\\d+}", "/profile/closeAudition/{\\d+}","/editBand",
//                        "/auditions/{\\d+}/applicants", "/auditions/{\\d+}/applicants/select/{\\d+}", "/profile/newMembership/{\\d+}", "/profile/bandMembers",
//                        "/profile/editMembership/{\\d+}", "/user/{\\d+}/invite").hasRole("BAND")
//                .antMatchers("/profile/**","/auditions/{\\d+}","/bandAuditions/{\\d+}", "/users/search", "/users", "/user/{\\d+}/bandMembers", "/user/{\\d+}/bands",
//                        "/profile/deleteMembership/{\\d+}").authenticated()
//                .antMatchers("/auditions","/auditions/search", "/", "/user/{\\d+}","/user/{\\d+}/profile-image").permitAll()
//TODO REVISAR TODOS
                .antMatchers(HttpMethod.GET, "/users").authenticated()
                .antMatchers(HttpMethod.PUT, "/users/{\\d+}/status").authenticated()
                .antMatchers(HttpMethod.GET, "/users/{\\d+}/status").authenticated()
                .antMatchers(HttpMethod.GET, "/users/{\\d+}/applications").hasRole("ARTIST")
                .antMatchers(HttpMethod.GET, "/memberships",
                        "/memberships/{\\d+}").authenticated() //TODO REVISAR CUAND VEAMOS EL ACCESO DESDE EL FRONT
                .antMatchers(HttpMethod.GET, "/auditions/{\\d+}/applications",
                        "/{auditionId}/applications/{\\d+}").hasRole("BAND")
                .antMatchers(HttpMethod.PUT, "/users/{\\d+}").authenticated()
                .antMatchers(HttpMethod.PUT, "/memberships/{\\d+}").hasRole("BAND")
                .antMatchers(HttpMethod.POST, "/auditions").hasRole("BAND")
                .antMatchers(HttpMethod.DELETE, "/memberships/{\\d+}").hasRole("BAND")

                .antMatchers("/**").permitAll()
                .and().addFilterBefore(authFilter,
                        FilterSecurityInterceptor.class); //TODO CHEQUEAR ESTO, SOTUYO DICE USERNAMEPASSWORDAUTHENTICACIONFILTER
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
        cors.setExposedHeaders(Arrays.asList("X-JWT", "X-Refresh-Token", "X-Content-Type-Options", "X-XSS-Protection", "X-Frame-Options",
                "authorization", "X-Total-Pages",
                "Content-Disposition"));
        //TODO ESTO EN PRODUCCION VUELA !!!!!!!!!!!!!!!!!!!!!!!!
        //cors.addAllowedOrigin("http://localhost:9000/");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cors);
        return source;
    }

}
