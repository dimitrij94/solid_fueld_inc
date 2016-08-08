package com.example.domain;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;

/**
 * Created by Dmitrij on 25.07.2016.
 */
@MappedSuperclass
public abstract class Person extends MappedEntity {

    public Person() {
    }

    public Person(String userName, String password, String phone, boolean enabled) {
        this.userName = userName;
        this.password = password;
        this.phone = phone;
        this.enabled = enabled;
    }

    @Pattern(regexp = "^[A-Z0-9_-]*$")
    @Size(min = 3, max = 25)
    private String userName;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).*$")
    @Size(min = 8)
    private String password;

    private String phone;

    private boolean enabled;

    public abstract Collection<? extends GrantedAuthority> getAuthorities();

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
}
