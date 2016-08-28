package com.example.services.client;

import com.example.domain.Client;
import com.example.services.ServiceI;
import javassist.NotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Created by Dmitrij on 25.07.2016.
 */
public interface ClientServiceI extends ServiceI<Client> {


    @Override
    Client find(Long id);

    @Override
    Client update(Client entity, Long id) throws NotFoundException;

    @Override
    void delete(Long id) throws NotFoundException;

    Client find(String phone);

    /**
     * @param phone
     * @param email
     * @return client which phone or email match with method arguments
     */
    Client find(String phone, String email);
}
