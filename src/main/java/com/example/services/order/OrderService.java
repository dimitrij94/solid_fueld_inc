package com.example.services.order;

import com.example.constants.OrderStatus;
import com.example.domain.Address;
import com.example.domain.Client;
import com.example.domain.ClientOrder;
import com.example.repositories.ClientOrderRepository;
import com.example.services.GenericService;
import com.example.services.address.AddressServiceI;
import com.example.services.client.ClientServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Created by Dmitrij on 28.07.2016.
 */
@Service
public class OrderService extends GenericService<ClientOrder> implements OrderServiceI {

    private ClientOrderRepository orderRepository;
    private ClientServiceI clientService;
    private AddressServiceI addressService;

    @Autowired
    public OrderService(ClientOrderRepository orderRepository,
                        ClientServiceI clientService,
                        AddressServiceI addressService) {
        this.orderRepository = orderRepository;
        this.clientService = clientService;
        this.addressService = addressService;
    }

    @Override
    public ClientOrder save(ClientOrder order) {
        Client client = order.getClient();
        Client c = clientService.find(client.getPhone(), client.getEmail());
        client = c == null ? clientService.save(client) : c;

        Address address = order.getOrderAddress();
        final Address orderAddress = address;
        address.setClient(client);
        List<Address> addresses = client.getAddress();
        if (addresses == null || addresses.isEmpty() || !addresses.contains(address)) {
            address = addressService.save(address);
        } else
            address = addresses.get(addresses.indexOf(orderAddress));

        order.setClient(client);
        order.setOrderAddress(address);
        return super.save(order);
    }


    @Override
    public Page<ClientOrder> query(int page, int numResults, OrderStatus orderStatus) {
        PageRequest pageRequest = new PageRequest(page, numResults);
        return orderRepository.findByStatus(orderStatus, pageRequest);
    }

    @Override
    public Page<ClientOrder> query(int page, int numResults, Long clientId) {
        PageRequest pageRequest = new PageRequest(page, numResults);
        return orderRepository.findByClientId(clientId, pageRequest);
    }

    @Override
    protected JpaRepository<ClientOrder, Long> getRepository() {
        return orderRepository;
    }
}
