package com.example.pkcn.dto.response;

import java.time.LocalDateTime;

public class ExceptionResponseDTO {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;

    public ExceptionResponseDTO(int status, String error, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
