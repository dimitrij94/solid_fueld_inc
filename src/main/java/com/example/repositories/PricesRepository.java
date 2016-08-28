package com.example.repositories;

import com.example.constants.PricesNames;
import com.example.domain.Prices;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Dmitrij on 14.08.2016.
 */
public interface PricesRepository extends JpaRepository<Prices, Long>{
    Prices findByPriceName(PricesNames priceName);
}
