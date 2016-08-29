package com.example.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by Dmitrij on 25.07.2016.
 */
@Entity
public class Address {

    public Address() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Address(String city, String street, Integer building) {
        this.city = city;
        this.street = street;
        this.building = building;
    }

    @Pattern(regexp = "^(([а-яіІА-ЯїЇєЄёЁa-zA-Z0-9.-_]+)([\t ])*)+$", flags = Pattern.Flag.CASE_INSENSITIVE)
    @Size(min = 3, max = 25)
    private String city;

    @Pattern(regexp = "^(([а-яіІА-ЯїЇєЄёЁa-zA-Z0-9.-_]+)([\t ])*)+$", flags = Pattern.Flag.CASE_INSENSITIVE)
    @Size(min = 3, max = 25)
    private String street;

    @NotNull
    private Integer building;

    private float latitude;
    private float longitude;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonIgnore
    private Client client;

    @OneToMany(mappedBy = "orderAddress")
    @JsonIgnore
    private List<ClientOrder> orders;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (!city.equals(address.city)) return false;
        if (!street.equals(address.street)) return false;
        return building.equals(address.building);

    }

    @Override
    public int hashCode() {
        int result = city.hashCode();
        result = 31 * result + street.hashCode();
        result = 31 * result + building.hashCode();
        return result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
