package com.example.domain;

import com.example.constants.Views;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table(name = "Client", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "email", "phone"}))
public class Client {

    public Client() {
    }

    public Client(String email, String name, Address address, String phone) {
        this.email = email;
        this.name = name;
        this.address = Collections.singletonList(address);
        this.phone = phone;
    }

    public Client(String email, String name, String phone) {
        this.email = email;
        this.name = name;
        this.phone = phone;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.OrderWithClientView.class)
    private Long id;

    @Email
    @NotBlank
    @JsonView(Views.OrderWithClientView.class)
    private String email;

    @Size(min = 2, max = 30)
    @Pattern(regexp = "^(([а-яіІА-ЯїЇєЄёЁa-zA-Z0-9]+)([\t ])*)+$", flags = Pattern.Flag.CASE_INSENSITIVE)
    @JsonView(Views.OrderWithClientView.class)
    private String name;

    @NotBlank
    @JsonView(Views.OrderWithClientView.class)
    private String phone;

    @OneToMany(mappedBy = "client")
    @JsonView(Views.OrderWithClientView.class)
    private List<Address> address;

    @OneToMany(mappedBy = "client")
    @JsonIgnore
    private List<ClientOrder> orders;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ClientOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<ClientOrder> orders) {
        this.orders = orders;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
