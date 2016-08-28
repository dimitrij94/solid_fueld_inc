package com.example;

import com.example.constants.Authorities;
import com.example.domain.Admin;
import com.example.services.admin.AdminServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.example"})
public class SolidFuelIncApplication {
    @Autowired
    private AdminServiceI adminService;

    public static void main(String[] args) {
        SpringApplication.run(SolidFuelIncApplication.class, args);
    }

    @PostConstruct
    public void construct() {
        if (adminService.findByUserName("Dimitrij94") == null)
            adminService.save(new Admin("Dimitrij94", "d147896325", "380989785514", true, Authorities.SUPER_ADMIN));

    }
}


