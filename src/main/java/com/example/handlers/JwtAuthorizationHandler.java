package com.example.handlers;

import com.example.adapters.AdminUserDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by Dmitrij on 29.07.2016.
 */
public class JwtAuthorizationHandler {

    private ObjectMapper objectMapper;
    private Mac hmac;

    private static final String TOKEN_SEPARATOR = ".";

    public JwtAuthorizationHandler(ObjectMapper objectMapper,
                                   Mac hmac) {
        this.objectMapper = objectMapper;
        this.hmac = hmac;
    }

    public AdminUserDetails tokenToDetails(String token) throws IOException {
        byte[] userBytes = validateToken(token);
        AdminUserDetails userDetails = fromJson(userBytes);
        //noinspection ConstantConditions
        if (new Date().getTime() < userDetails.getExpires().getTime())
            return userDetails;
        return null;
    }

    public synchronized String clientDetailsToToken(AdminUserDetails userDetails) throws JsonProcessingException {
        byte[] userBytes = toJSON(userDetails);
        byte[] hash = hmac.doFinal(userBytes);
        //noinspection StringBufferReplaceableByString
        StringBuilder builder = new StringBuilder(170);
        builder.append(toBase64(userBytes));
        builder.append(TOKEN_SEPARATOR);
        builder.append(toBase64(hash));
        return builder.toString();
    }


    private byte[] validateToken(String token) {
        final String[] parts = token.split(TOKEN_SEPARATOR);
        //token is decripted from base64
        byte[] userBytes = fromBase64(parts[0]);
        //token hashed on server with secret key, this hash is used to secure token from 3-d role altering
        byte[] hash = fromBase64(parts[1]);
        boolean validHash = Arrays.equals(createHmac(userBytes), hash);
        if (validHash) return userBytes;
        else throw new IllegalArgumentException();
    }

    private byte[] fromBase64(String base64) {
        return Base64.decodeBase64(base64);
    }

    private String toBase64(byte[] content) {
        return Base64.encodeBase64String(content);
    }

    private AdminUserDetails fromJson(byte[] input) throws IOException {
        return objectMapper.readValue(input, AdminUserDetails.class);
    }

    private byte[] toJSON(AdminUserDetails content) throws JsonProcessingException {
        return objectMapper.writeValueAsBytes(content);
    }

    private synchronized byte[] createHmac(byte[] content) {
        return hmac.doFinal(content);
    }

}
