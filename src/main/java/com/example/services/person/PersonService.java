package com.example.services.person;

import com.example.domain.Person;
import com.example.services.GenericService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.*;

/**
 * Created by Dmitrij on 25.07.2016.
 */

@org.springframework.stereotype.Service
public class PersonService extends GenericService<Person> {


    @Override
    protected JpaRepository<Person, Long> getRepository() {
        return null;
    }
}
