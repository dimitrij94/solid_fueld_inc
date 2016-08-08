package com.example.adapters;

import com.example.domain.Admin;
import com.example.domain.Person;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

/**
 * Created by Dmitrij on 30.07.2016.
 */
public class AdminUserDetails extends User implements UserDetails {
    private Admin person;
    private final String phone;
    private static final long TEN_DAYS = 1000 * 60 * 60 * 24 * 10;
    private final Date expires;

    public AdminUserDetails(Admin admin) {
        super(admin.getUserName(), admin.getPassword(), admin.isEnabled(), true, true, true, admin.getAuthorities());
        this.person = admin;
        this.expires = new Date(System.currentTimeMillis() + TEN_DAYS);
        this.phone = this.person.getPhone();
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
