package com.wildbeeslabs.api.rest.common.service.interfaces;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * JPA Base REST Application Service declaration
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 * @param <T>
 * @param <ID>
 */
public interface IBaseService<T extends Object, ID extends Serializable> {

    static enum DateTypeOrder {
        BEFORE, AFTER;
    }

    static enum SortTypeOrder {
        ASC, DESC;
    }

    Optional<T> findById(final ID id);

    void create(final T item);

    void save(final T item);

    void update(final T item);

    void merge(final T itemTo, final T itemFrom);

    void deleteById(final ID id);

    void delete(final T item);

    void delete(final List<? extends T> item);

    List<? extends T> findAll();

    Page<? extends T> findAll(final Pageable pageable);

    void deleteAll();

    boolean isExist(final ID id);

    boolean isExist(final T item);
}
