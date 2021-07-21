package com.nineleaps.productservice.exception;

/**
 * @author Dilip Chauhan
 * Created on 30/03/2020
 */
public class MethodNotAllowedException extends RuntimeException {

    public MethodNotAllowedException() {
        super();
    }

    public MethodNotAllowedException(String message) {
        super(message);
    }

    public MethodNotAllowedException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
