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
package com.wildbeeslabs.api.rest.common.model.dto.converter;

import com.wildbeeslabs.api.rest.common.model.IBaseEntity;
import com.wildbeeslabs.api.rest.common.model.dto.IBaseDTO;
import com.wildbeeslabs.api.rest.common.model.dto.wrapper.IBaseDTOListWrapper;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Alex
 */
@Service("dtoConverter")
public final class DTOConverter {

    /**
     * Default Logger instance
     */
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private ModelMapper modelMapper;

    public <M extends IBaseEntity, N extends IBaseDTO> N convertToDTO(final M itemEntity, final Class<? extends N> clazz) {
        N itemDto = modelMapper.map(itemEntity, clazz);
        return itemDto;
    }

    public <M extends IBaseEntity, N extends IBaseDTO> List<? extends N> convertToDTO(final List<? extends M> itemEntityList, final Class<? extends N> clazz) {
        List<? extends N> itemDtoList = itemEntityList.stream()
                .map(item -> convertToDTO(item, clazz))
                .collect(Collectors.toCollection(LinkedList::new));
        return itemDtoList;
    }

    public <N extends IBaseDTO, M extends IBaseEntity> M convertToEntity(final N itemDto, final Class<? extends M> clazz) {
        M itemEntity = modelMapper.map(itemDto, clazz);
        return itemEntity;
    }

    public <N extends IBaseDTO, M extends IBaseEntity> List<? extends M> convertToEntity(final List<? extends N> itemDtoList, final Class<? extends M> clazz) {
        if (Objects.isNull(itemDtoList)) {
            return null;
        }
        List<? extends M> itemEntityList = itemDtoList.stream()
                .map(item -> convertToEntity(item, clazz))
                .collect(Collectors.toCollection(LinkedList::new));
        return itemEntityList;
    }

    public <N extends IBaseDTO> List<? extends N> unwrap(final IBaseDTOListWrapper<? extends N> dtoListWrapper) {
        if (Objects.isNull(dtoListWrapper)) {
            return null;
        }
        return dtoListWrapper.getItems();
    }

    public <N extends IBaseDTO, M extends IBaseEntity> List<? extends M> convertToEntityAndUnwrap(final IBaseDTOListWrapper<? extends N> dtoListWrapper, final Class<? extends M> clazz) {
        List<? extends N> itemDtoList = this.unwrap(dtoListWrapper);
        return this.convertToEntity(itemDtoList, clazz);
    }

    public <N extends IBaseDTO> IBaseDTOListWrapper<? extends N> wrap(final List<? extends N> itemDtoList, final Class<? extends IBaseDTOListWrapper> classListWrapper) {
        IBaseDTOListWrapper<N> listWrapper = null;
        try {
            listWrapper = classListWrapper.newInstance();
            listWrapper.setItems(itemDtoList);
        } catch (InstantiationException | IllegalAccessException ex) {
            LOGGER.error("Cannot instantiate DTOListWrapper object, message = {}", ex);
        }
        return listWrapper;
    }

    public <M extends IBaseEntity, N extends IBaseDTO> IBaseDTOListWrapper<? extends N> convertToDTOAndWrap(final List<? extends M> itemEntityList, final Class<? extends N> clazz, final Class<? extends IBaseDTOListWrapper> classListWrapper) {
        List<? extends N> itemDtoList = this.convertToDTO(itemEntityList, clazz);
        return this.wrap(itemDtoList, classListWrapper);
    }
}
