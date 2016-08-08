package com.example.services;

import javassist.NotFoundException;

/**
 * Created by Dmitrij on 25.07.2016.
 */
public interface ServiceI<T> {
    T save(T entity);
    T find(Long id);
    T update(T entity, Long id) throws NotFoundException;
    void delete(Long id) throws NotFoundException;
}
