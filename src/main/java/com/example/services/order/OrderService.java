package com.example.services.order;

import com.example.domain.ClientOrder;
import com.example.repositories.ClientOrderRepository;
import com.example.services.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by Dmitrij on 28.07.2016.
 */
@Service
public class OrderService extends GenericService<ClientOrder> implements OrderServiceI {

    ClientOrderRepository orderRepository;

    @Autowired
    public OrderService(ClientOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    protected JpaRepository<ClientOrder, Long> getRepository() {
        return orderRepository;
    }
}
