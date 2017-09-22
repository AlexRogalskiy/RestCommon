package com.wildbeeslabs.api.rest.common.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.wildbeeslabs.api.rest.common.exception.BadRequestException;
import com.wildbeeslabs.api.rest.common.exception.EmptyContentException;
import com.wildbeeslabs.api.rest.common.exception.ResourceAlreadyExistException;
import com.wildbeeslabs.api.rest.common.exception.ResourceNotFoundException;
import com.wildbeeslabs.api.rest.common.exception.ServiceException;

import java.util.Date;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import org.hibernate.exception.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * BaseResponseException REST handler
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
@RestControllerAdvice
public class BaseResponseExceptionHandler {//extends ResponseEntityExceptionHandler {

    public enum ResponseStatusCode {
        RESOURCE_ALREADY_EXIST(101),
        BAD_REQUEST(102),
        RESOURCE_NOT_FOUND(103),
        EMPTY_CONTENT(104),
        SERVICE_ERROR(105),
        BAD_MEDIA_TYPE(106),
        METHOD_NOT_ALLOWED(107),
        MEDIA_TYPE_MISMATCH(109),
        FORBIDDEN(110);

        private final Integer value;

        private ResponseStatusCode(final Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(this.getValue());
        }

        public static ResponseStatusCode valueOf(final Integer value) {
            for (ResponseStatusCode v : values()) {
                if (Objects.equals(v.getValue(), value)) {
                    return v;
                }
            }
            throw new IllegalArgumentException();
        }
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JacksonXmlRootElement(localName = "exception")
    protected class ExceptionEntity {

        @JacksonXmlProperty(localName = "path")
        private String path;
        @JacksonXmlProperty(localName = "code")
        private Integer code;
        @JacksonXmlProperty(localName = "error")
        private String error;
        @JacksonXmlProperty(localName = "message")
        private String message;
        @JacksonXmlProperty(localName = "timestamp")
        //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtils.DEFAULT_DATE_FORMAT_PATTERN_EXT, locale = DateUtils.DEFAULT_DATE_FORMAT_LOCALE)
        private Date timestamp;

        public ExceptionEntity() {
        }

        public ExceptionEntity(final String path, final ResponseStatusCode statusCode, final String message) {
            this(path, statusCode.getValue(), statusCode.name(), message);
        }

        public ExceptionEntity(final String path, final Integer code, final String error, final String message) {
            this.path = path;
            this.code = code;
            this.error = error;
            this.message = message;
            this.timestamp = new Date();
        }

        public String getPath() {
            return path;
        }

        public void setPath(final String path) {
            this.path = path;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(final Integer code) {
            this.code = code;
        }

        public String getError() {
            return error;
        }

        public void setError(final String error) {
            this.error = error;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(final String message) {
            this.message = message;
        }

        public Date getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(final Date timestamp) {
            this.timestamp = timestamp;
        }
    }

    /**
     * Default Logger instance
     */
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());//BaseResponseExceptionHandler.class

    @ExceptionHandler({ResourceAlreadyExistException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    protected ResponseEntity<?> handle(final HttpServletRequest req, final ResourceAlreadyExistException ex) {
        LOGGER.error(ex.getMessage());
        String url = req.getRequestURI().substring(req.getContextPath().length());
        return new ResponseEntity<>(new ExceptionEntity(url, ResponseStatusCode.RESOURCE_ALREADY_EXIST, ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({BadRequestException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<?> handle(final HttpServletRequest req, final BadRequestException ex) {
        LOGGER.error(ex.getMessage());
        String url = req.getRequestURI().substring(req.getContextPath().length());
        return new ResponseEntity<>(new ExceptionEntity(url, ResponseStatusCode.BAD_REQUEST, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<?> handle(final HttpServletRequest req, final ResourceNotFoundException ex) {
        LOGGER.error(ex.getMessage());
        String url = req.getRequestURI().substring(req.getContextPath().length());
        return new ResponseEntity<>(new ExceptionEntity(url, ResponseStatusCode.RESOURCE_NOT_FOUND, ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({EmptyContentException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    protected ResponseEntity<?> handle(final HttpServletRequest req, final EmptyContentException ex) {
        LOGGER.error(ex.getMessage());
        String url = req.getRequestURI().substring(req.getContextPath().length());
        return new ResponseEntity<>(new ExceptionEntity(url, ResponseStatusCode.EMPTY_CONTENT, ex.getMessage()), HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler({TypeMismatchException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    protected ResponseEntity<?> handle(final HttpServletRequest req, final TypeMismatchException ex) {
        LOGGER.error(ex.getMessage());
        String url = req.getRequestURI().substring(req.getContextPath().length());
        return new ResponseEntity<>(new ExceptionEntity(url, ResponseStatusCode.BAD_MEDIA_TYPE, ex.getMessage()), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class, DataIntegrityViolationException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<?> handle(final HttpServletRequest req, final MethodArgumentNotValidException ex) {
        LOGGER.error(ex.getMessage());
        String url = req.getRequestURI().substring(req.getContextPath().length());
        return new ResponseEntity<>(new ExceptionEntity(url, ResponseStatusCode.BAD_REQUEST, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({javax.validation.ConstraintViolationException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<?> handle(final HttpServletRequest req, final javax.validation.ConstraintViolationException ex) {
        LOGGER.error(ex.getMessage());
        String url = req.getRequestURI().substring(req.getContextPath().length());
        return new ResponseEntity<>(new ExceptionEntity(url, ResponseStatusCode.BAD_REQUEST, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MissingPathVariableException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<?> handle(final HttpServletRequest req, final MissingPathVariableException ex) {
        LOGGER.error(ex.getMessage());
        String url = req.getRequestURI().substring(req.getContextPath().length());
        return new ResponseEntity<>(new ExceptionEntity(url, ResponseStatusCode.BAD_REQUEST, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    protected ResponseEntity<?> handle(final HttpServletRequest req, final HttpRequestMethodNotSupportedException ex) {
        LOGGER.error(ex.getMessage());
        String url = req.getRequestURI().substring(req.getContextPath().length());
        return new ResponseEntity<>(new ExceptionEntity(url, ResponseStatusCode.METHOD_NOT_ALLOWED, ex.getMessage()), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<?> handle(final HttpServletRequest req, final HttpMessageNotReadableException ex) {
        LOGGER.error(ex.getMessage());
        String url = req.getRequestURI().substring(req.getContextPath().length());
        return new ResponseEntity<>(new ExceptionEntity(url, ResponseStatusCode.BAD_REQUEST, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    protected ResponseEntity<?> handle(final HttpServletRequest req, final HttpMediaTypeNotSupportedException ex) {
        LOGGER.error(ex.getMessage());
        String url = req.getRequestURI().substring(req.getContextPath().length());
        return new ResponseEntity<>(new ExceptionEntity(url, ResponseStatusCode.MEDIA_TYPE_MISMATCH, ex.getMessage()), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler({ServiceException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<?> handle(final HttpServletRequest req, final ServiceException ex) {
        LOGGER.error(ex.getMessage());
        String url = req.getRequestURI().substring(req.getContextPath().length());
        return new ResponseEntity<>(new ExceptionEntity(url, ResponseStatusCode.SERVICE_ERROR, ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({AccessDeniedException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected ResponseEntity<?> handle(final HttpServletRequest req, final AccessDeniedException ex) {
        LOGGER.error(ex.getMessage());
        String url = req.getRequestURI().substring(req.getContextPath().length());
        return new ResponseEntity<>(new ExceptionEntity(url, ResponseStatusCode.FORBIDDEN, ex.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    protected ResponseEntity<?> handle(final HttpServletRequest req, final HttpMediaTypeNotAcceptableException ex) {
        LOGGER.error(ex.getMessage());
        String url = req.getRequestURI().substring(req.getContextPath().length());
        return new ResponseEntity<>(new ExceptionEntity(url, ResponseStatusCode.BAD_MEDIA_TYPE, ex.getMessage()), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }
}
