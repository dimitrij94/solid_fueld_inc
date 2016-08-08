package com.example.repositories;

import com.example.domain.Admin;
import com.example.domain.Client;
import com.example.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Created by Dmitrij on 25.07.2016.
 */
@NoRepositoryBean
public interface GenericPersonRepository<T extends Person> extends JpaRepository<T, Long>{
    @Query("SELECT e FROM #{#entityName} e WHERE e.userName = ?1 OR e.phone = ?2")
    T findByUserNameOrPhone(String userName, String phone);

    T findByUserName(String userName);
}
