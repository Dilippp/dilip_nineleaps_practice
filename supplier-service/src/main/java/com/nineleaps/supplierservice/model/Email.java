package com.nineleaps.supplierservice.model;

import lombok.Builder;
import lombok.Getter;

/**
 * @author Dilip Chauhan
 * Created on 30/03/2020
 */
@Builder
@Getter
public class Email {
    private String to;
    private String[] cc;
    private String[] bcc;
    private String subject;
    private String text;
}
