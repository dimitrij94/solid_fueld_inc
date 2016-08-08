package com.example.domain;

import com.example.constants.OrderStatus;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by Dmitrij on 24.07.2016.
 */
@Entity
public class ClientOrder extends MappedEntity {
    @NotNull
    private Integer quantityKG;
    private String requestContent;

    @Enumerated(EnumType.ORDINAL)
    private OrderStatus status;

    @Past
    @Temporal(TemporalType.DATE)
    private Date orderMade;

    @Past
    @Temporal(TemporalType.DATE)
    private Date orderClosed;

    @ManyToOne
    @JoinColumn(name = "client")
    private Client client;

    public Integer getQuantityKG() {
        return quantityKG;
    }

    public void setQuantityKG(Integer quantityKG) {
        this.quantityKG = quantityKG;
    }

    public String getRequestContent() {
        return requestContent;
    }

    public void setRequestContent(String requestContent) {
        this.requestContent = requestContent;
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
}
