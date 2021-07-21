package com.nineleaps.orderservice.controller.advice;

import com.nineleaps.orderservice.exception.BadRequestException;
import com.nineleaps.orderservice.exception.ForbiddenException;
import com.nineleaps.orderservice.exception.MethodNotAllowedException;
import com.nineleaps.orderservice.exception.ResourceNotFoundException;
import com.nineleaps.orderservice.exception.ServiceException;
import com.nineleaps.orderservice.exception.UnauthorizedException;
import com.nineleaps.orderservice.exception.response.ExceptionResponse;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Global controller advice. A global exception handler.
 *
 * @author Dilip Chauhan
 * Created on 22/03/2020
 */
@RestControllerAdvice
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@NoArgsConstructor
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

    /**
     * A method to handle service exception and response with custom error object.
     *
     * @param serviceException the service exception
     * @return the exception response
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse serviceException(final ServiceException serviceException) {
        return processError(serviceException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Method not allowed exception handler.
     *
     * @param notAllowedException the method not allowed exception
     * @return the exception response
     */
    @ExceptionHandler(MethodNotAllowedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ExceptionResponse methodNotAllowedException(final MethodNotAllowedException notAllowedException) {
        return processError(notAllowedException, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * Forbidden exception handler.
     *
     * @param forbiddenException the forbidden exception
     * @return the exception response
     */
    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse forbiddenException(final ForbiddenException forbiddenException) {
        return processError(forbiddenException, HttpStatus.FORBIDDEN);
    }

    /**
     * Bad request exception handler.
     *
     * @param badRequestException the bad request exception
     * @return the exception response
     */
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse badRequestException(final BadRequestException badRequestException) {
        return processError(badRequestException, HttpStatus.BAD_REQUEST);
    }

    /**
     * Constraint violation exception handler.
     *
     * @param violationException the constraint violation exception
     * @return the exception response
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse constraintViolationException(final ConstraintViolationException violationException) {
        return processError(violationException, HttpStatus.BAD_REQUEST);
    }

    /**
     * Resource not found exception handler.
     *
     * @param notFoundException the resource not found exception
     * @return the exception response
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse notFoundException(final ResourceNotFoundException notFoundException) {
        return processError(notFoundException, HttpStatus.NOT_FOUND);
    }

    /**
     * Unauthorized exception handler.
     *
     * @param unauthorizedException the unauthorized exception
     * @return the exception response
     */
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionResponse unauthorizedException(final UnauthorizedException unauthorizedException) {
        return processError(unauthorizedException, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Generic exception handler method.
     *
     * @param exception the exception
     * @return the exception response
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse exception(final Exception exception) {
        return processError(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * A method to handle field validation errors.
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return response entity with custom error message
     */
    @Override
    @SuppressWarnings("all")
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        Map<String, Object> hashMap = new LinkedHashMap<>();
        Map<String, Set<String>> setMap = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toSet())));
        hashMap.put("errors", setMap);
        return new ResponseEntity<>(hashMap, headers, status);
    }

    private ExceptionResponse processError(final Exception exception, final HttpStatus httpStatus) {
        final String message = Optional.of(exception.getMessage()).orElse(exception.getClass().getSimpleName());
        return new ExceptionResponse(httpStatus.value(), message);
    }
}
