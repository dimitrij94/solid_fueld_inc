package com.example.controllers;

import com.example.domain.ClientOrder;
import com.example.services.order.OrderServiceI;
import com.example.services.ServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Dmitrij on 28.07.2016.
 */
@Controller
@RequestMapping("/order")
public class OrderController extends GenericController<ClientOrder> {

    private OrderServiceI orderService;

    @Autowired
    public OrderController(OrderServiceI orderService) {
        this.orderService = orderService;
    }

    @Override
    protected ServiceI<ClientOrder> getService() {
        return orderService;
    }
}
