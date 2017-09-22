package com.wildbeeslabs.api.rest.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * EmptyContent REST Application exception
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
@ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Empty content")
public class EmptyContentException extends Exception {

    private static final long serialVersionUID = 1L;

    public EmptyContentException() {
    }

    public EmptyContentException(String message) {
        super(message);
    }

    public EmptyContentException(Throwable cause) {
        super(cause);
    }

    public EmptyContentException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
