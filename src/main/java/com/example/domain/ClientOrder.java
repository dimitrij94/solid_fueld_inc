package com.example.domain;

import com.example.constants.OrderStatus;
import com.example.constants.Views;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

/**
 * Created by Dmitrij on 24.07.2016.
 */
@Entity
public class ClientOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private Integer quantityKG;

    @Enumerated(EnumType.ORDINAL)
    private OrderStatus status;

    @Past
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderMade;

    @Past
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderClosed;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "client_address_id")
    private Address orderAddress;

    public ClientOrder() {
        this.orderMade = new Date(System.currentTimeMillis());
        this.status = OrderStatus.RECEIVED;
    }

    public ClientOrder(Integer quantityKG, String message, Client client) {
        this.client = client;
        this.quantityKG = quantityKG;
        this.orderMade = new Date(System.currentTimeMillis());
        this.status = OrderStatus.RECEIVED;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantityKG() {
        return quantityKG;
    }

    public void setQuantityKG(Integer quantityKG) {
        this.quantityKG = quantityKG;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Date getOrderMade() {
        return orderMade;
    }

    public void setOrderMade(Date orderMade) {
        this.orderMade = orderMade;
    }

    public Date getOrderClosed() {
        return orderClosed;
    }

    public void setOrderClosed(Date orderClosed) {
        this.orderClosed = orderClosed;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Address getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(Address orderAddress) {
        this.orderAddress = orderAddress;
    }
}
