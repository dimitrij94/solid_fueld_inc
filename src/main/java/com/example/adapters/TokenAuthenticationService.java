package com.example.adapters;

import com.example.adapters.AdminAuthentication;
import com.example.adapters.AdminUserDetails;
import com.example.handlers.JwtAuthorizationHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Dmitrij on 30.07.2016.
 */

public class TokenAuthenticationService {
    public static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";

    private JwtAuthorizationHandler tokenHandler;

    public TokenAuthenticationService(JwtAuthorizationHandler tokenHandler) {
        this.tokenHandler = tokenHandler;
    }

    public void addAuthentication(HttpServletResponse response, AdminUserDetails userDetails) throws JsonProcessingException {
        response.addHeader(AUTH_HEADER_NAME, tokenHandler.clientDetailsToToken(userDetails));
    }

    public Authentication getAuthentication(HttpServletRequest request) throws IOException {
        final String token = request.getHeader(AUTH_HEADER_NAME);
        final AdminUserDetails userDetails;
        if (token != null) {
            userDetails = tokenHandler.tokenToDetails(token);
            if (userDetails != null)
                return new AdminAuthentication(userDetails);
        }
        return null;
    }
}
