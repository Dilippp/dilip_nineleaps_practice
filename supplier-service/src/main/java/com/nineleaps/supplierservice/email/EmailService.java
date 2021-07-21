package com.nineleaps.supplierservice.email;

import com.nineleaps.supplierservice.model.Email;

/**
 * @author Dilip Chauhan
 * Created on 30/03/2020
 */
public interface EmailService {

    void sendSimpleMessage(Email email);

    void sendMessageWithAttachment(Email email, String attachmentFilename, String attachmentPath);
}
