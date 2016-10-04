package com.example.services;

import com.example.domain.Admin;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.Future;

/**
 * Created by Dmitrij on 25.07.2016.
 */
public abstract class GenericService<T> implements ServiceI<T> {

    protected abstract JpaRepository<T, Long> getRepository();

    String notFoundErrorMessage = "No such entity found";

    @Override
    public Page<T> query(int page, int numResults) {
        PageRequest request = new PageRequest(page, numResults);
        return getRepository().findAll(request);
    }

    @Override
    public long count() {
        return getRepository().count();
    }

    @Override
    @Transactional
    public T save(T entity) {
        return getRepository().save(entity);
    }

    @Override
    public T find(Long id) {
        return getRepository().findOne(id);
    }

    @Override
    @Transactional
    public T update(T entity, Long id) throws NotFoundException {
        if (find(id) != null)
            return save(entity);
        else throw new NotFoundException(notFoundErrorMessage);
    }

    @Override
    @Transactional
    public void delete(Long id) throws NotFoundException {
        if (find(id) != null)
            getRepository().delete(id);
        else throw new NotFoundException(notFoundErrorMessage);
    }

    @Override
    @Async
    public Future<T> asyncPost(T entity) {
        return new AsyncResult<T>(save(entity));
    }

    @Async
    @Override
    public Future<T> asyncPut(T entity, Long id) throws NotFoundException {
        if (find(id) != null)
            return new AsyncResult<T>(save(entity));
        else throw new NotFoundException(notFoundErrorMessage);
    }

    @Async
    @Override
    public void asyncDelete(Long id) throws NotFoundException {
        delete(id);
    }

}
