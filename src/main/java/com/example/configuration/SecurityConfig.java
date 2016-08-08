package com.example.configuration;

import com.example.adapters.MyUserDetailService;
import com.example.adapters.TokenAuthenticationService;
import com.example.constants.Authorities;
import com.example.filters.CsrfTokenToCookieFilter;
import com.example.filters.StatelessAuthenticationFilter;
import com.example.filters.StatelessLoginFilter;
import com.example.handlers.JwtAuthorizationHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Dmitrij on 25.07.2016.
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String HMAC_ALGO = "HmacSHA256";

    @Value("${mac_secret_key}")
    String secretKey;

    @Autowired
    @Qualifier("myUserDetailService")
    UserDetailsService userDetailsService;

    @Autowired
    StatelessLoginFilter statelessLoginFilter;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    StatelessAuthenticationFilter statelessAuthenticationFilter;

    private byte[] secretKey() {
        return secretKey.getBytes();
    }

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
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.GET, "/").authenticated()
                .antMatchers(HttpMethod.POST, "/").hasRole(Authorities.ROLE_ADMIN_VAL)
                .antMatchers(HttpMethod.DELETE, "/").hasRole(Authorities.ROLE_ADMIN_VAL)
                .antMatchers(HttpMethod.PUT, "/").hasRole(Authorities.ROLE_ADMIN_VAL)
                .antMatchers(HttpMethod.DELETE, "/admin").denyAll();

        http.addFilterBefore(statelessLoginFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(statelessAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }

    @Bean
    Mac mac() {
        Mac hmac;
        try {
            hmac = Mac.getInstance(HMAC_ALGO);
            hmac.init(new SecretKeySpec(secretKey(), HMAC_ALGO));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new IllegalStateException("failed to initialize HMAC: " + e.getMessage(), e);
        }
        return hmac;
    }


    @Bean
    JwtAuthorizationHandler jwtAuthorizationHandler() {
        return new JwtAuthorizationHandler(objectMapper, mac());
    }

    @Bean
    TokenAuthenticationService tokenAuthenticationService() {
        return new TokenAuthenticationService(jwtAuthorizationHandler());
    }

    @Bean
    StatelessLoginFilter statelessLoginFilter() {
        return new StatelessLoginFilter(antPathRequestMatcher(), objectMapper, userDetailsService(),authenticationManager(),)
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
