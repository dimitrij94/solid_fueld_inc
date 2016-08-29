package com.example.configuration;

import com.example.constants.Authorities;
import com.example.filters.CsrfTokenToCookieFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Created by Dmitrij on 25.07.2016.
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String HMAC_ALGO = "HmacSHA256";

    @Autowired
    @Qualifier("myUserDetailService")
    UserDetailsService userDetailsService;

    @Autowired
    ObjectMapper objectMapper;

    @Bean
    public AntPathRequestMatcher antPathRequestMatcher() {
        return new AntPathRequestMatcher("/login");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.httpBasic();
        http.addFilterAfter(new CsrfTokenToCookieFilter(), CsrfFilter.class);
        http.csrf().csrfTokenRepository(csrfTokenRepository());
        http.userDetailsService(userDetailsService);
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/admin/**").hasRole(Authorities.SUPER_ADMIN_VAL)
                .antMatchers(HttpMethod.GET, "/admin").hasAnyRole(Authorities.ROLE_ADMIN_VAL, Authorities.SUPER_ADMIN_VAL)
                .antMatchers(HttpMethod.POST, "/admin").hasRole(Authorities.SUPER_ADMIN_VAL)
                .antMatchers(HttpMethod.PUT, "/admin").hasRole(Authorities.SUPER_ADMIN_VAL)
                .antMatchers(HttpMethod.DELETE, "/admin").hasRole(Authorities.SUPER_ADMIN_VAL)

                .antMatchers(HttpMethod.POST, "/order", "/client").permitAll()
                .antMatchers(HttpMethod.GET, "/").hasRole(Authorities.ROLE_ADMIN_VAL)
                .regexMatchers(HttpMethod.POST, "/").hasRole(Authorities.ROLE_ADMIN_VAL)
                .regexMatchers(HttpMethod.DELETE, "/").hasRole(Authorities.ROLE_ADMIN_VAL)
                .regexMatchers(HttpMethod.PUT, "/").hasRole(Authorities.ROLE_ADMIN_VAL)
                .antMatchers(HttpMethod.GET, "/").permitAll();
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }
}
