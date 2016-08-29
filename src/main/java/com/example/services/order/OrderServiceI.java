package com.example.services.order;

import com.example.constants.OrderStatus;
import com.example.domain.ClientOrder;
import com.example.services.ServiceI;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Created by Dmitrij on 28.07.2016.
 */
public interface OrderServiceI extends ServiceI<ClientOrder> {
    @Override
    ClientOrder save(ClientOrder entity);

    @Override
    ClientOrder find(Long id);

    @Override
    ClientOrder update(ClientOrder entity, Long id) throws NotFoundException;

    @Override
    void delete(Long id) throws NotFoundException;

    Page<ClientOrder> query(int page, int numResults, OrderStatus status);

    Page<ClientOrder> query(int page, int numResults, Long clientId);
}
