package com.example.services.order;

import com.example.domain.ClientOrder;
import com.example.services.ServiceI;
import javassist.NotFoundException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Created by Dmitrij on 28.07.2016.
 */
public interface OrderServiceI extends ServiceI<ClientOrder> {
    @Override
    @PreAuthorize("hasRole('ROLE_CLIENT')||hasRole('ROLE_ADMIN')")
    ClientOrder save(ClientOrder entity);

    @Override
    @PostAuthorize("(hasRole('ROLE_CLIENT')&&returnObject.client.id==principal.client.id)||hasRole('ROLE_ADMIN')")
    ClientOrder find(Long id);

    @Override
    @PreAuthorize("(hasRole('ROLE_CLIENT') && #entity.client.id==principal.client.id)||hasRole('ROLE_ADMIN')")
    ClientOrder update(ClientOrder entity, Long id) throws NotFoundException;

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void delete(Long id) throws NotFoundException;
}
