package com.example.repositories;

import com.example.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Dmitrij on 08.08.2016.
 */
public interface AdminRepository extends JpaRepository<Admin, Long> {
    public Admin findByUserName(String userName);
}
