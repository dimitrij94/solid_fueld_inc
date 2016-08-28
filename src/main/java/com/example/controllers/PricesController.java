package com.example.controllers;

import com.example.constants.PricesNames;
import com.example.domain.Prices;
import com.example.repositories.PricesRepository;
import com.example.services.PriceServiceI;
import com.example.services.ServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.Properties;

/**
 * Created by Dmitrij on 14.08.2016.
 */
@RestController
@RequestMapping("/price")
public class PricesController extends GenericController<Prices> {
    private final PriceServiceI priceService;

    @Autowired
    public PricesController(PriceServiceI priceService) {
        this.priceService = priceService;
    }

    @RequestMapping(method = RequestMethod.PUT, params = {"priceName"})
    public Prices updatePrice(@RequestBody int newPrice, @RequestParam("priceName") String priceName) {
        PricesNames price = PricesNames.valueOf(priceName);
        return priceService.update(new Prices(price, newPrice), price);
    }

    @RequestMapping(method = RequestMethod.GET, params = {"priceName"})
    public Prices getPrice(@RequestParam("priceName") String priceName) {
        return priceService.find(PricesNames.valueOf(priceName));
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Prices> getAllPrices() {
        return priceService.findAllPrices();
    }

    @Override
    protected ServiceI<Prices> getService() {
        return priceService;
    }
}
