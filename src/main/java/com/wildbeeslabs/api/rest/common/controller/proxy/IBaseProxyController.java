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
import com.wildbeeslabs.api.rest.common.model.dto.wrapper.IBaseDTOListWrapper;

import java.io.Serializable;
import java.util.List;

/**
 *
 * Base Proxy REST Controller declaration
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 * @param <T>
 * @param <E>
 * @param <ID>
 */
public interface IBaseProxyController<T extends Serializable, E extends Serializable, ID extends Serializable> {

    IBaseDTOListWrapper<? extends E> getAllItems() throws EmptyContentException;

    E getItemById(final ID id);

    //T createItem(final E itemDto, Class<? extends T> entityClass);
    E createItem(final E itemDto);

    E updateItem(final ID id, final E itemDto);
    //T updateItem(final ID id, final E itemDto, Class<? extends T> entityClass);

    E deleteItem(final ID id);

    void deleteItems(final List<? extends E> itemDtoList);

    void deleteAllItems();
}
