package com.example.services;

import javassist.NotFoundException;
import org.springframework.data.domain.Page;

/**
 * Created by Dmitrij on 25.07.2016.
 */
public interface ServiceI<T> {
    Page<T> query(int page, int numResults);
    T save(T entity);
    T find(Long id);
    T update(T entity, Long id) throws NotFoundException;
    void delete(Long id) throws NotFoundException;
}
