package com.example.services;

import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.Future;

/**
 * Created by Dmitrij on 25.07.2016.
 */
public interface ServiceI<T> {
    Page<T> query(int page, int numResults);
    T save(T entity);
    T find(Long id);
    T update(T entity, Long id) throws NotFoundException;
    void delete(Long id) throws NotFoundException;
    long count();

    Future<T> asyncPost(T entity);

    @Async
    Future<T> asyncPut(T entity, Long id) throws NotFoundException;

    @Async
    void asyncDelete(Long id) throws NotFoundException;
}
