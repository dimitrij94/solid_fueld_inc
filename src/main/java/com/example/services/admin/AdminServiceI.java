package com.example.services.admin;

import com.example.domain.Admin;
import com.example.services.ServiceI;

/**
 * Created by Dmitrij on 08.08.2016.
 */
public interface AdminServiceI extends ServiceI<Admin> {
    Admin findByUserName(String userName);
}
