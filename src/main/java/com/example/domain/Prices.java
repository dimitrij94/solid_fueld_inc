package com.example.domain;

import com.example.constants.PricesNames;

import javax.persistence.*;

/**
 * Created by Dmitrij on 14.08.2016.
 */
@Entity
@Table(name = "Prices",
        uniqueConstraints = @UniqueConstraint(columnNames = {"priceName"}))
public class Prices {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private float price;

    @Enumerated(EnumType.STRING)
    private PricesNames priceName;

    public Prices() {
    }

    public Prices(PricesNames priceName, int price) {
        this.priceName = priceName;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PricesNames getPriceName() {
        return priceName;
    }

    public void setPriceName(PricesNames priceName) {
        this.priceName = priceName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
