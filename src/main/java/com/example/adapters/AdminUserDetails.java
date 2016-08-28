package com.example.adapters;

import com.example.constants.Authorities;
import com.example.domain.Admin;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * Created by Dmitrij on 30.07.2016.
 */
public class AdminUserDetails implements UserDetails {

    private Admin person;
    private String password;
    private final String username;
    private final List<SimpleGrantedAuthority> authorities;
    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final boolean enabled;
    private final String phone;
    private static final long TEN_DAYS = 1000 * 60 * 60 * 24 * 10;
    private final Date expires;

    public AdminUserDetails(Admin admin) {
        this.authorities = Collections.singletonList(new SimpleGrantedAuthority(admin.getAuthorities().getRole()));
        this.username = admin.getUserName();
        this.password = admin.getPassword();

        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = admin.isEnabled();
        this.person = admin;
        this.expires = new Date(System.currentTimeMillis() + TEN_DAYS);
        this.phone = this.person.getPhone();
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public List<SimpleGrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public Admin getPerson() {
        return person;
    }

    public void setPerson(Admin person) {
        this.person = person;
    }

    public Date getExpires() {
        return expires;
    }

    public String getPhone() {
        return phone;
    }
}
