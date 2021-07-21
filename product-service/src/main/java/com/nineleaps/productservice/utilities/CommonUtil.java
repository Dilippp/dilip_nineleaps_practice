package com.nineleaps.productservice.utilities;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

/**
 * @author Dilip Chauhan
 * Created on 30/03/2020
 */
@Slf4j
public final class CommonUtil {
    private CommonUtil() {

    }

    public static String convertInputStreamToString(InputStream inputStream) {
        StringWriter writer = new StringWriter();
        try {
            IOUtils.copy(inputStream, writer, StandardCharsets.UTF_8);
            return writer.toString();
        } catch (IOException e) {
            log.error("Error in converting input stream to string" + e.getMessage(), e);
        }
        return "";
    }
}
