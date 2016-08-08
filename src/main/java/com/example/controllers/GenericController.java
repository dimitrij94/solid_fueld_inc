package com.example.controllers;

import com.example.services.ServiceI;
import javassist.NotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Dmitrij on 24.07.2016.
 */
public abstract class GenericController<T> {

    private ServiceI<T> service = getService();

    protected abstract ServiceI<T> getService();

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public T find(@PathVariable("id") Long id) {
        return service.find(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public T post(@Validated @RequestBody T entity) {
        return service.save(entity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public T upadate(@Validated @RequestBody T entity, @PathVariable("id") Long id) throws NotFoundException {
        return service.update(entity, id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) throws NotFoundException {
        service.delete(id);
    }
}
