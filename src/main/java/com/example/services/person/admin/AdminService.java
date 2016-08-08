package com.example.services.person.admin;

import com.example.domain.Admin;
import com.example.repositories.AdminRepository;
import com.example.services.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by Dmitrij on 08.08.2016.
 */
@Service
public class AdminService extends GenericService<Admin> implements AdminServiceI {
    AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    protected JpaRepository<Admin, Long> getRepository() {
        return adminRepository;
    }

    @Override
    public Admin findByUserName(String userName) {
        return adminRepository.findByUserName(userName);
    }
}
