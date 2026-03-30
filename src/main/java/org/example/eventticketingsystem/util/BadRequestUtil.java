package org.example.eventticketingsystem.util;

import org.example.eventticketingsystem.exception.BadRequestException;

import java.util.HashMap;
import java.util.Map;

public class BadRequestUtil {

    private final Map<String, String> errors = new HashMap<>();

    public BadRequestUtil add(Boolean condition, String field, String message) {
        if (condition) errors.put(field, message);
        return this;
    }

    public void validate() {
        if (!errors.isEmpty()) {
            throw new BadRequestException(errors);
        }
    }
}