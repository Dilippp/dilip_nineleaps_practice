package com.nineleaps.orderservice.exception;

/**
 * @author Dilip Chauhan
 * Created on 22/03/2020
 */
public class ForbiddenException extends RuntimeException {

    public ForbiddenException() {
        super();
    }

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
