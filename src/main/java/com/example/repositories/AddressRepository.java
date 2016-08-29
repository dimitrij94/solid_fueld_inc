package com.example.repositories;

import com.example.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Dmitrij on 18.08.2016.
 */
public interface AddressRepository extends JpaRepository<Address, Long> {
}
