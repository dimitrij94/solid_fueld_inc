package com.example.services;

import com.example.constants.PricesNames;
import com.example.domain.Prices;

/**
 * Created by Dmitrij on 14.08.2016.
 */
public interface PriceServiceI extends ServiceI<Prices> {

    Prices find(PricesNames priceName);

    Prices update(Prices pricesDTO, PricesNames priceName);

    Iterable<Prices> findAllPrices();

}
