package com.example.filters;

import com.example.adapters.AdminUserDetails;
import com.example.adapters.AdminAuthentication;
import com.example.adapters.TokenAuthenticationService;
import com.example.handlers.JwtAuthorizationHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Dmitrij on 30.07.2016.
 */
/*
filter provedes initial authorization with username password for users that have not yet recived a token
*/
public class StatelessLoginFilter extends AbstractAuthenticationProcessingFilter {
    private ObjectMapper objectMapper;
    private UserDetailsService userDetailsService;
    private AuthenticationManager authenticationManager;
    private TokenAuthenticationService tokenAuthenticationService;

    @Autowired
    public StatelessLoginFilter(RequestMatcher requestMatcher,
                                ObjectMapper objectMapper,
                                UserDetailsService userDetailsService,
                                AuthenticationManager authenticationManager,
                                TokenAuthenticationService tokenAuthenticationService) {
        super(requestMatcher);
        this.objectMapper = objectMapper;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.tokenAuthenticationService = tokenAuthenticationService;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        AdminUserDetails userDetails = objectMapper.readValue(request.getInputStream(), AdminUserDetails.class);
        String userName = userDetails.getUsername();
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(userName != null ? userName : userDetails.getPhone(), userDetails.getPassword());
        return authenticationManager.authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {

        final AdminUserDetails adminUserDetails = (AdminUserDetails) userDetailsService.loadUserByUsername(authResult.getName());
        AdminAuthentication authentication = new AdminAuthentication(adminUserDetails);
        tokenAuthenticationService.addAuthentication(response, adminUserDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
