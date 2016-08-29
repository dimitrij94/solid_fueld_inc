package com.example.controllers;

import com.example.constants.OrderStatus;
import com.example.domain.ClientOrder;
import com.example.services.order.OrderServiceI;
import com.example.services.ServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.Future;

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
/*
    @RequestMapping(method = RequestMethod.POST, params = {"asynch"})
    public Future<ClientOrder> postClientOrder(@RequestBody @Valid ClientOrder clientOrder, BindingResult result){
        if(!result.hasErrors())
            return new AsyncResult<>();
    }
*/
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
