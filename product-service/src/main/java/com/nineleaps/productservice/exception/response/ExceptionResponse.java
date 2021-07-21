package com.nineleaps.productservice.exception.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Dilip Chauhan
 * Created on 30/03/2020
 */
@Setter
@Getter
@AllArgsConstructor
public final class ExceptionResponse {

    private int status;
    private String error;
}
