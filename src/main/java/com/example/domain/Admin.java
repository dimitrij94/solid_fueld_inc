package com.example.domain;

import com.example.constants.Authorities;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Entity;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Dmitrij on 08.08.2016.
 */
@Entity
public class Admin extends Person {

    private transient static Set<GrantedAuthority> authorities = new HashSet<>(2);

    public Admin() {
    }

    public Admin(String userName, String password, String phone, boolean enabled) {
        super(userName, password, phone, enabled);
    }

    static {
        authorities.add(new SimpleGrantedAuthority(Authorities.ROLE_ADMIN));
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
