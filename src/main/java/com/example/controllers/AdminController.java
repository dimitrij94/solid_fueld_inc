package com.example.controllers;

import com.example.adapters.AdminUserDetails;
import com.example.domain.Admin;
import com.example.services.ServiceI;
import com.example.services.admin.AdminServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created by Dmitrij on 16.08.2016.
 */
@RestController
@RequestMapping("/admin")
public class AdminController extends GenericController<Admin> {
    private final AdminServiceI adminService;

    @Autowired
    public AdminController(AdminServiceI adminService) {
        this.adminService = adminService;
    }

    @Override
    protected ServiceI<Admin> getService() {
        return adminService;
    }

    @RequestMapping
    public Principal getAdmin(Principal admin) {
        return admin;
    }
}
