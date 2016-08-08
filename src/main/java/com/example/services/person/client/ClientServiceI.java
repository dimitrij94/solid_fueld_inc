package com.example.services.person.client;

import com.example.domain.Client;
import com.example.services.ServiceI;
import javassist.NotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Created by Dmitrij on 25.07.2016.
 */
public interface ClientServiceI extends ServiceI<Client> {


    @Override
    @PreAuthorize("(hasRole('ROLE_CLIENT') && #id==principal.client.id)||hasRole('ROLE_ADMIN')")
    Client find(Long id);

    @Override
    @PreAuthorize("(hasRole('ROLE_CLIENT') && #id==principal.client.id)||hasRole('ROLE_ADMIN')")
    Client update(Client entity, Long id) throws NotFoundException;

    @Override
    @PreAuthorize("(hasRole('ROLE_CLIENT') && #id==principal.client.id)||hasRole('ROLE_ADMIN')")
    void delete(Long id) throws NotFoundException;
}
