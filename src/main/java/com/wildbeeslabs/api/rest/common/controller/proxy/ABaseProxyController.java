/*
 * The MIT License
 *
 * Copyright 2017 Pivotal Software, Inc..
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.wildbeeslabs.api.rest.common.controller.proxy;

import com.wildbeeslabs.api.rest.common.exception.EmptyContentException;
import com.wildbeeslabs.api.rest.common.exception.ResourceAlreadyExistException;
import com.wildbeeslabs.api.rest.common.exception.ResourceNotFoundException;
import com.wildbeeslabs.api.rest.common.model.IBaseEntity;
import com.wildbeeslabs.api.rest.common.model.dto.wrapper.BaseDTOListWrapper;
import com.wildbeeslabs.api.rest.common.model.dto.converter.DTOConverter;
import com.wildbeeslabs.api.rest.common.model.dto.IBaseDTO;
import com.wildbeeslabs.api.rest.common.model.dto.wrapper.IBaseDTOListWrapper;
import com.wildbeeslabs.api.rest.common.utils.ResourceUtils;
import com.wildbeeslabs.api.rest.common.service.interfaces.IJpaBaseService;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * Base Proxy REST Controller implementation
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 * @param <T>
 * @param <E>
 * @param <ID>
 * @param <S>
 */
public abstract class ABaseProxyController<T extends IBaseEntity, E extends IBaseDTO, ID extends Serializable, S extends IJpaBaseService<T, ID>> implements IBaseProxyController<T, E, ID> {

    /**
     * Default Logger instance
     */
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private ResourceUtils resourceUtils;
    @Autowired
    private DTOConverter dtoConverter;
    @Autowired
    private S service;

    @Override
    public IBaseDTOListWrapper<? extends E> getAllItems() throws EmptyContentException {
        List<? extends T> items = this.getAllEntityItems();
        return getDTOConverter().convertToDTOAndWrap(items, getDtoClass(), getDtoListClass());
    }

    public List<? extends T> getAllEntityItems() throws EmptyContentException {
        LOGGER.info("Fetching all items");
        List<? extends T> items = getService().findAll();
        if (items.isEmpty()) {
            throw new EmptyContentException(getResource().formatMessage("error.no.content"));
        }
        return items;
    }

    public T getEntityItemById(final ID id) {
        LOGGER.info("Fetching item by id {}", id);
        T item = getService().findById(id);
        if (Objects.isNull(item)) {
            throw new ResourceNotFoundException(getResource().formatMessage("error.no.item.id", id));
        }
        return item;
    }

    @Override
    public E getItemById(final ID id) {
        T item = this.getEntityItemById(id);
        return getDTOConverter().convertToDTO(item, getDtoClass());
    }

    public T createItem(final E itemDto, final Class<? extends T> entityClass) {
        LOGGER.info("Creating item {}", itemDto);
        T itemEntity = getDTOConverter().convertToEntity(itemDto, entityClass);
        if (getService().isExist(itemEntity)) {
            throw new ResourceAlreadyExistException(getResource().formatMessage("error.already.exist.item"));
        }
        getService().create(itemEntity);
        return itemEntity;
    }

    @Override
    public E createItem(final E itemDto) {
        T item = this.createItem(itemDto, getEntityClass());
        return getDTOConverter().convertToDTO(item, getDtoClass());
    }

    public T updateItem(final ID id, final E itemDto, final Class<? extends T> entityClass) {
        LOGGER.info("Updating item by id {}", id);
        T currentItem = getService().findById(id);
        if (Objects.isNull(currentItem)) {
            throw new ResourceNotFoundException(getResource().formatMessage("error.no.item.id", id));
        }
        T itemEntity = getDTOConverter().convertToEntity(itemDto, entityClass);
        getService().merge(currentItem, itemEntity);
        return currentItem;
    }

    @Override
    public E updateItem(final ID id, final E itemDto) {
        T item = this.updateItem(id, itemDto, getEntityClass());
        return getDTOConverter().convertToDTO(item, getDtoClass());
    }

    public T deleteEntityItem(final ID id) {
        LOGGER.info("Deleting item by id {}", id);
        T item = getService().findById(id);
        if (Objects.isNull(item)) {
            throw new ResourceNotFoundException(getResource().formatMessage("error.no.item.id", id));
        }
        getService().deleteById(id);
        return item;
    }

    @Override
    public E deleteItem(final ID id) {
        T item = this.deleteEntityItem(id);
        return getDTOConverter().convertToDTO(item, getDtoClass());
    }

    @Override
    public void deleteItems(final List<? extends E> itemDtoList) {
        LOGGER.info("Deleting items {}", StringUtils.join(itemDtoList, ", "));
        List<? extends T> items = getDTOConverter().convertToEntity(itemDtoList, getEntityClass());
        getService().delete(items);
    }

    @Override
    public void deleteAllItems() {
        LOGGER.info("Deleting all items");
        getService().deleteAll();
    }

    protected S getService() {
        return this.service;
    }

    protected DTOConverter getDTOConverter() {
        return this.dtoConverter;
    }

    protected ResourceUtils getResource() {
        return this.resourceUtils;
    }

//    protected Class<? extends T> getEntityClass() {
//        return (Class<? extends T>) BaseEntity.class;
//    }
//
//    protected Class<? extends E> getDtoClass() {
//        return (Class<? extends E>) BaseDTO.class;
//    }
    protected abstract Class<? extends T> getEntityClass();

    protected abstract Class<? extends E> getDtoClass();

    protected Class<? extends IBaseDTOListWrapper> getDtoListClass() {
        return BaseDTOListWrapper.class;
    }
}
