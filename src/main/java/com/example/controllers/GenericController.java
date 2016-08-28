package com.example.controllers;

import com.example.services.ServiceI;
import com.fasterxml.jackson.annotation.JsonView;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Dmitrij on 24.07.2016.
 */
public abstract class GenericController<T> {

    protected abstract ServiceI<T> getService();

    public final static int pageSize = 50;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public T find(@PathVariable("id") Long id) {
        return getService().find(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public T post(@Validated @RequestBody T entity) {
        return getService().save(entity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public T upadate(@Validated @RequestBody T entity, @PathVariable("id") Long id) throws NotFoundException {
        return getService().update(entity, id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) throws NotFoundException {
        getService().delete(id);
    }

    @RequestMapping(method = RequestMethod.GET, params = {"page"})
    public Page<T> query(@RequestParam("page") int page) {
        return getService().query(page, pageSize);
    }
}
