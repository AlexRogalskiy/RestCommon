package com.wildbeeslabs.api.rest.common.controller;

import java.io.Serializable;
import org.springframework.http.ResponseEntity;

/**
 *
 * Base REST Controller declaration
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 * @param <T>
 * @param <E>
 */
public interface IBaseController<T extends Serializable, E extends Serializable> {

    ResponseEntity<?> getAll();

    ResponseEntity<?> getById(final Long id);

    ResponseEntity<?> create(final E item);

    ResponseEntity<?> update(final Long id, final E item);

    ResponseEntity<?> delete(final Long id);

    ResponseEntity<?> deleteAll();
}
