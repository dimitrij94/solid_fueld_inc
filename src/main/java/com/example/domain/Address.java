package com.example.domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by Dmitrij on 25.07.2016.
 */
@Entity
public class Address extends MappedEntity {

    public Address() {
    }

    public Address(String city, String street, Integer building) {
        this.city = city;
        this.street = street;
        this.building = building;
    }

    @Pattern(regexp = "^[a-zА-Я-_]*$")
    @Size(min = 3, max = 25)
    private String city;

    @Pattern(regexp = "^[a-zА-Я-_]*$")
    @Size(min = 3, max = 25)
    private String street;

    @NotNull
    private Integer building;

    private int appartment;

    @ManyToOne
    @JoinColumn(name = "client")
    private Client client;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getBuilding() {
        return building;
    }

    public void setBuilding(Integer building) {
        this.building = building;
    }

    public int getAppartment() {
        return appartment;
    }

    public void setAppartment(int appartment) {
        this.appartment = appartment;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
