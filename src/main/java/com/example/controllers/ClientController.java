package com.example.controllers;

import com.example.domain.Client;
import com.example.services.person.client.ClientServiceI;
import com.example.services.ServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Dmitrij on 28.07.2016.
 */
@Controller
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
