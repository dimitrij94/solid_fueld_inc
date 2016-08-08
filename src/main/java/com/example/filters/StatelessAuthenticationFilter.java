package com.example.filters;

import com.example.adapters.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Dmitrij on 30.07.2016.
 */
@Service
public class StatelessAuthenticationFilter extends GenericFilterBean {

    private TokenAuthenticationService tokenAuthenticationService;

    @Autowired
    public StatelessAuthenticationFilter(TokenAuthenticationService tokenAuthenticationService) {
        this.tokenAuthenticationService = tokenAuthenticationService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(
                tokenAuthenticationService.getAuthentication((HttpServletRequest) servletRequest));
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
