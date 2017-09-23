package com.wildbeeslabs.api.rest.common.controller;

import com.wildbeeslabs.api.rest.common.controller.proxy.IBaseProxyController;
import com.wildbeeslabs.api.rest.common.exception.EmptyContentException;
import com.wildbeeslabs.api.rest.common.model.IBaseEntity;
import com.wildbeeslabs.api.rest.common.model.dto.IBaseDTO;

import java.beans.PropertyEditorSupport;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * Abstract Base REST Controller implementation
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 * @param <T>
 * @param <E>
 * @param <ID>
 * @param <C>
 */
public abstract class ABaseController<T extends IBaseEntity, E extends IBaseDTO, ID extends Serializable, C extends IBaseProxyController<T, E, ID>> implements IBaseController<T, E, ID> {

    @Autowired
    protected HttpServletRequest request;
    @Autowired
    private C proxyController;

    @Override
    public ResponseEntity<?> getAll() {
        try {
            return new ResponseEntity<>(getProxyController().getAllItems(), HttpStatus.OK);
        } catch (EmptyContentException ex) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @Override
    public ResponseEntity<?> getById(final ID id) {
        return new ResponseEntity<>(getProxyController().getItemById(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> create(final E itemDto) {
        E itemEntity = getProxyController().createItem(itemDto);
        UriComponentsBuilder ucBuilder = UriComponentsBuilder.newInstance();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path(request.getRequestURI() + "/{id}").buildAndExpand(itemEntity.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> update(final ID id, final E itemDto) {
        E itemEntity = getProxyController().updateItem(id, itemDto);
        return new ResponseEntity<>(itemEntity, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> delete(final ID id) {
        E itemEntity = getProxyController().deleteItem(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteAll() {
        getProxyController().deleteAllItems();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    protected static class BaseEnumConverter<U extends Enum<U>> extends PropertyEditorSupport {

        private final Class<U> type;

        public BaseEnumConverter(final Class<U> type) {
            this.type = type;
        }

        @Override
        public void setAsText(final String text) throws IllegalArgumentException {
            U item = Enum.valueOf(this.type, text.toUpperCase());
            setValue(item);
        }
    }

    protected C getProxyController() {
        return this.proxyController;
    }
}
