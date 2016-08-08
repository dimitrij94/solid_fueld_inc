package com.example.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Created by Dmitrij on 24.07.2016.
 */
@MappedSuperclass
abstract class MappedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MappedEntity entity = (MappedEntity) o;

        return id.equals(entity.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
