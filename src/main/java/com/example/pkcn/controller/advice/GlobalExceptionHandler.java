package com.example.pkcn.controller.advice;

import com.example.pkcn.controller.advice.cus_exception.EmailAlreadyExistsException;
import com.example.pkcn.dto.response.ExceptionResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponseDTO> handleEmailExist(EmailAlreadyExistsException ex) {
        ExceptionResponseDTO error = new ExceptionResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                "BAD REQUEST",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponseDTO> handleIllegalArgument(IllegalArgumentException ex) {
        ExceptionResponseDTO error = new ExceptionResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                "INVALID INPUT",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDTO> handleGlobalException(Exception ex) {
        ExceptionResponseDTO error = new ExceptionResponseDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "INTERNAL SERVER ERROR",
                "Hệ thống gặp sự cố, vui lòng thử lại sau!"
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}