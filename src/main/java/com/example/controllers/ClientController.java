package com.example.controllers;

import com.example.constants.Views;
import com.example.domain.Client;
import com.example.services.client.ClientServiceI;
import com.example.services.ServiceI;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Dmitrij on 28.07.2016.
 */
@RestController
@RequestMapping("/client")
public class ClientController extends GenericController<Client> {

    private ClientServiceI clientService;

    @Autowired
    public ClientController(ClientServiceI clientService) {
        this.clientService = clientService;
    }

    @Override
    protected ServiceI<Client> getService() {
        return clientService;
    }

}
