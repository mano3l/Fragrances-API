package com.personal.fragrances.infra;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private ControllerExceptionHandler.ErrorCode errorCode;
    private Map<String, String> errors;
}