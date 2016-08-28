package com.example.repositories;

import com.example.constants.OrderStatus;
import com.example.domain.ClientOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Dmitrij on 25.07.2016.
 */
public interface ClientOrderRepository extends JpaRepository<ClientOrder, Long> {
    Page<ClientOrder> findByStatus(OrderStatus received, Pageable pageRequest);
    Page<ClientOrder> findByClientId(Long clientId, Pageable pageRequest);
}
