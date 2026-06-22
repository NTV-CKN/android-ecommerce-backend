package com.example.pkcn.controller.advice;

import com.example.pkcn.controller.advice.cus_exception.*;
import com.example.pkcn.dto.response.ExceptionResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ExceptionResponseDTO> handleEmailExist(DataNotFoundException ex) {
        ex.printStackTrace();
        ExceptionResponseDTO error = new ExceptionResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                "BAD REQUEST",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponseDTO> handleEmailExist(EmailAlreadyExistsException ex) {
        ex.printStackTrace();
        ExceptionResponseDTO error = new ExceptionResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                "BAD REQUEST",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalUserStatusException.class)
    public ResponseEntity<ExceptionResponseDTO> handleIllegalUserStatus(IllegalUserStatusException ex) {
        ex.printStackTrace();
        ExceptionResponseDTO error = new ExceptionResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                "BAD REQUEST",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalFormatDataException.class)
    public ResponseEntity<ExceptionResponseDTO> handleIllegalFormatData(IllegalFormatDataException ex) {
        ex.printStackTrace();
        ExceptionResponseDTO error = new ExceptionResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                "BAD REQUEST",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataInvalidException.class)
    public ResponseEntity<ExceptionResponseDTO> handleDataInvalid(DataInvalidException ex) {
        ex.printStackTrace();
        ExceptionResponseDTO error = new ExceptionResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                "BAD REQUEST",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataStillValidException.class)
    public ResponseEntity<ExceptionResponseDTO> handleDataNotInvalidation(DataStillValidException ex) {
        ex.printStackTrace();
        ExceptionResponseDTO error = new ExceptionResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                "BAD REQUEST",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotExistException.class)
    public ResponseEntity<ExceptionResponseDTO> handleUserNotExist(UserNotExistException ex) {
        ex.printStackTrace();
        ExceptionResponseDTO error = new ExceptionResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                "BAD REQUEST",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ExceptionResponseDTO> handleUserNotExist(LoginException ex) {
        ex.printStackTrace();
        ExceptionResponseDTO error = new ExceptionResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                "BAD REQUEST",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponseDTO> handleIllegalArgument(IllegalArgumentException ex) {
        ex.printStackTrace();
        ExceptionResponseDTO error = new ExceptionResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                "INVALID INPUT",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDTO> handleGlobalException(Exception ex) {
        ex.printStackTrace();
        ExceptionResponseDTO error = new ExceptionResponseDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "INTERNAL SERVER ERROR",
                "Hệ thống gặp sự cố, vui lòng thử lại sau!"
        );
        System.out.println(ex.getCause());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}