package com.example.services.client;

import com.example.domain.Address;
import com.example.domain.Client;
import com.example.repositories.ClientRepository;
import com.example.services.GenericService;
import com.example.services.address.AddressServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by Dmitrij on 25.07.2016.
 */
@Service
public class ClientService extends GenericService<Client> implements ClientServiceI {

    private ClientRepository repository;

    @Autowired
    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<Client, Long> getRepository() {
        return repository;
    }

    @Override
    public Client find(String phone) {
        return repository.findByPhone(phone);
    }

    @Override
    public Client find(String phone, String email) {
        return repository.findByPhoneOrEmail(phone, email);
    }
}
