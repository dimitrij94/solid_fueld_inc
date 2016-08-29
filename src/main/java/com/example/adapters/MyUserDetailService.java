package com.example.adapters;

import com.example.domain.Admin;
import com.example.services.admin.AdminServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by Dmitrij on 25.07.2016.
 */
@Service
public class MyUserDetailService implements UserDetailsService {
    private AdminServiceI adminServiceI;

    @Autowired
    public MyUserDetailService(AdminServiceI adminServiceI) {
        this.adminServiceI = adminServiceI;
    }

    @Override
    public AdminUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminServiceI.findByUserName(username);
        if (admin != null) return new AdminUserDetails(admin);
        throw new BadCredentialsException("No such user found");
    }
}
