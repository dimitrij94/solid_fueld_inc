package com.example.repositories;

import com.example.domain.ClientOrder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Dmitrij on 25.07.2016.
 */
public interface ClientOrderRepository extends JpaRepository<ClientOrder, Long> {

}
