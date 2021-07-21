package com.nineleaps.orderservice.utilities;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

/**
 * A utility class to perform some common conversions.
 *
 * @author Dilip Chauhan
 * Created on 22/03/2020
 */
@Slf4j
public final class CommonUtil {
    private CommonUtil() {

    }

    /**
     * Convert input stream to string.
     *
     * @param inputStream the input stream
     * @return the string
     */
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
