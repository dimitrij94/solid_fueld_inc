package com.example.services;

import javassist.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Dmitrij on 25.07.2016.
 */
public abstract class GenericService<T> implements ServiceI<T> {

    protected abstract JpaRepository<T, Long> getRepository();

    private JpaRepository<T, Long> repository = getRepository();

    private final String notFoundErrorMessage = "No such entity found";

    @Override
    @Transactional
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    public T find(Long id) {
        return repository.findOne(id);
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
            repository.delete(id);
        else throw new NotFoundException("No such entity found");
    }
}
