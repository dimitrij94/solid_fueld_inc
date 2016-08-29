package com.example.repositories;

import com.example.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by Dmitrij on 24.07.2016.
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByPhone(String phone);

    Client findByPhoneOrEmail(String phone, String email);
}
