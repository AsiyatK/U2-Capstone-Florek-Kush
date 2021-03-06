package com.trilogyed.adminapiservice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    public void configurationAuthentication(AuthenticationManagerBuilder authBuilder) throws Exception {
        PasswordEncoder encoder = new BCryptPasswordEncoder();

        authBuilder.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(
                        "select username, password, enabled from users where username = ?")
                .authoritiesByUsernameQuery(
                        "select username, authority from authorities where username = ?")
                .passwordEncoder(encoder);
    }

    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic();

        httpSecurity.authorizeRequests()

                .mvcMatchers(HttpMethod.PUT, "/customers/*", "/inventory/*", "/invoices/*", "/level-up/*", "products/*").hasAnyAuthority( "TEAM_LEAD", "MANAGER", "ADMIN")
                .mvcMatchers(HttpMethod.POST, "/customers/*", "/inventory/*", "/invoices/*", "/level-up/*", "products/*").hasAnyAuthority("MANAGER", "ADMIN")
                .mvcMatchers(HttpMethod.DELETE, "/customers/*", "/inventory/*", "/invoices/*", "/level-up/*", "products/*").hasAuthority("ADMIN")
                .mvcMatchers(HttpMethod.GET, "/customers/*", "/inventory/*", "/invoices/*", "/level-up/*", "products/*").hasAnyAuthority( "TEAM_LEAD", "MANAGER", "ADMIN", "EMPLOYEE")
                .mvcMatchers(HttpMethod.POST, "/customers").hasAuthority("TEAM_LEAD");

//                .anyRequest().permitAll();



        //to log user out
        httpSecurity
                .logout()
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/allDone")
                .deleteCookies("JSESSIONID")
                .deleteCookies("XREF-TOKEN")
                .invalidateHttpSession(true);

        httpSecurity
                .csrf()/*.disable();*/
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }


}
