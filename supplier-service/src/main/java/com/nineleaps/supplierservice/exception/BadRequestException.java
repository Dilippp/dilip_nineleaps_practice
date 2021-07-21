package com.nineleaps.supplierservice.exception;

/**
 * @author Dilip Chauhan
 * Created on 30/03/2020
 */
public class BadRequestException extends RuntimeException {

    public BadRequestException() {
        super();
    }

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
