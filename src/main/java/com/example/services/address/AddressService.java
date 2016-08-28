package com.example.services.address;

import com.example.domain.Address;
import com.example.domain.Client;
import com.example.repositories.AddressRepository;
import com.example.services.GenericService;
import com.example.services.client.ClientServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Dmitrij on 18.08.2016.
 */
@Service
public class AddressService extends GenericService<Address> implements AddressServiceI {
    private final AddressRepository addressRepository;


    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    protected JpaRepository<Address, Long> getRepository() {
        return addressRepository;
    }


}
