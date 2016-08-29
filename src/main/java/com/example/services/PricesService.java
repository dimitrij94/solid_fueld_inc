package com.example.services;

import com.example.constants.PricesNames;
import com.example.domain.Prices;
import com.example.repositories.PricesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by Dmitrij on 14.08.2016.
 */
@Service
public class PricesService extends GenericService<Prices> implements PriceServiceI {
    private final PricesRepository pricesRepository;

    @Autowired
    public PricesService(PricesRepository pricesRepository) {
        this.pricesRepository = pricesRepository;
    }

    @Override
    protected JpaRepository<Prices, Long> getRepository() {
        return pricesRepository;
    }

    @Override
    public Prices find(PricesNames priceName) {
        return pricesRepository.findByPriceName(priceName);
    }

    @Override
    public Prices update(Prices pricesDTO, PricesNames priceName) {
        Prices price = find(priceName);
        if (price != null) pricesDTO.setId(price.getId());
        else
            throw new IllegalArgumentException("there is no price with such name");
        return save(pricesDTO);
    }

    @Override
    public Iterable<Prices> findAllPrices() {
        return pricesRepository.findAll();
    }
}
