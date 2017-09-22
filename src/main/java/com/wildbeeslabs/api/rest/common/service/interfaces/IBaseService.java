package com.wildbeeslabs.api.rest.common.service.interfaces;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * Base REST Application Service declaration
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 * @param <T>
 */
public interface IBaseService<T extends Serializable> {

    static enum DateTypeOrder {
        BEFORE, AFTER;
    }

    static enum SortTypeOrder {
        ASC, DESC;
    }

    T findById(final Long id);

    void create(final T item);

    void save(final T item);

    void update(final T item);

    void merge(final T itemTo, final T itemFrom);

    void deleteById(final Long id);

    void delete(final T item);

    void delete(final List<? extends T> item);

    List<T> findAll();

    Page<T> findAll(final Pageable pageable);

    void deleteAll();

    boolean isExist(final T item);
}
