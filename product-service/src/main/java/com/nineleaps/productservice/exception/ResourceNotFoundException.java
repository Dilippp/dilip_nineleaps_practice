package com.nineleaps.productservice.exception;

/**
 * @author Dilip Chauhan
 * Created on 30/03/2020
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
