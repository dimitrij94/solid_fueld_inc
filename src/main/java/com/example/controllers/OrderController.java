package com.example.controllers;

import com.example.constants.OrderStatus;
import com.example.domain.ClientOrder;
import com.example.services.order.OrderServiceI;
import com.example.services.ServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Dmitrij on 28.07.2016.
 */
@RestController
@RequestMapping("/order")
public class OrderController extends GenericController<ClientOrder> {

    private OrderServiceI orderService;

    private final static int ordersPageSize = 50;

    @RequestMapping(method = RequestMethod.GET, params = {"page", "status"})
    public Page<ClientOrder> query(@RequestParam("page") int page, @RequestParam("status") String status) {
        return orderService.query(page, ordersPageSize, OrderStatus.valueOf(status));
    }


    @RequestMapping(method = RequestMethod.GET, params = {"page", "clientId"})
    public Page<ClientOrder> query(@RequestParam("Page") int page, @RequestParam("clientId") Long clientId) {
        return orderService.query(page, ordersPageSize, clientId);
    }

    @Autowired
    public OrderController(OrderServiceI orderService) {
        this.orderService = orderService;
    }

    @Override
    protected ServiceI<ClientOrder> getService() {
        return orderService;
    }
}
