package com.example.domain;

import com.example.constants.Authorities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Dmitrij on 08.08.2016.
 */
@Entity
@Table(name = "Admin", uniqueConstraints = @UniqueConstraint(columnNames = {"userName"}))
public class Admin {

    public Admin(String userName, String password, String phone, boolean enabled) {
        this.userName = userName;
        this.password = password;
        this.phone = phone;
        this.enabled = enabled;
        this.authorities = Authorities.ADMIN;
    }
    public Admin(String userName, String password, String phone, boolean enabled, Authorities authorities) {
        this.userName = userName;
        this.password = password;
        this.phone = phone;
        this.enabled = enabled;
        this.authorities = authorities;
    }

    public Admin() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9-_\\.]{3,25}$", flags = {Pattern.Flag.CASE_INSENSITIVE})
    @Size(min = 3, max = 25)
    private String userName;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).*$")
    @Size(min = 8)
    private String password;

    private String phone;

    private boolean enabled;

    @Enumerated(EnumType.ORDINAL)
    private Authorities authorities;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Authorities getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Authorities authorities) {
        this.authorities = authorities;
    }
}
