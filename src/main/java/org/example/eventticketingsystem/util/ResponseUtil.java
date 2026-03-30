package org.example.eventticketingsystem.util;

import org.example.eventticketingsystem.model.response.ApiResponse;
import org.springframework.http.HttpStatus;

public class ResponseUtil {

    public static <T> ApiResponse<T> build(String message, HttpStatus status, T payload) {
        return ApiResponse.<T>builder()
                .message(message)
                .status(status)
                .payload(payload)
                .build();
    }

    public static ApiResponse<Void> build(String message, HttpStatus status) {
        return build(message, status, null);
    }

    public static <T> ApiResponse<T> success(String message, T payload) {
        return build("Successfully " + message, HttpStatus.OK, payload);
    }

    public static <T> ApiResponse<T> created(String message, T payload) {
        return build("Created " + message + " successfully", HttpStatus.CREATED, payload);
    }

    public static ApiResponse<Void> deleted(String message) {
        return build("Deleted " + message + " successfully", HttpStatus.OK, null);
    }
}