package org.senla.share.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.warn("MethodArgumentNotValidException: ", ex);
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.warn("MissingServletRequestParameterException: ", ex);
        String error = ex.getParameterName() + " parameter is missing";
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        logger.warn("MethodArgumentTypeMismatchException: ", ex);
        String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.warn("NoHandlerFoundException: ", ex);
        String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), error);

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.warn("HttpRequestMethodNotSupportedException: ", ex);
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" method is not supported for this request. Supported methods are ");
        ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));
        ApiError apiError = new ApiError(HttpStatus.METHOD_NOT_ALLOWED, ex.getLocalizedMessage(), builder.toString());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.warn("HttpMediaTypeNotSupportedException: ", ex);
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t + ", "));

        ApiError apiError = new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                ex.getLocalizedMessage(), builder.substring(0, builder.length() - 2));
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex) {
        logger.warn("AuthenticationException: ", ex);
        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, ex.getLocalizedMessage(), ex.getLocalizedMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
        logger.warn("EntityNotFoundException: ", ex);
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), ex.getLocalizedMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        logger.warn("AccessDeniedException: ", ex);
        ApiError apiError = new ApiError(HttpStatus.FORBIDDEN, ex.getLocalizedMessage(), ex.getLocalizedMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException(final NoSuchElementException ex, WebRequest request) {
        logger.warn("NoSuchElementException: ", ex);
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), ex.getLocalizedMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }


    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        logger.warn("Exception: ", ex);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiError apiError = new ApiError(status, ex.getLocalizedMessage(), ex.getLocalizedMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }
}
