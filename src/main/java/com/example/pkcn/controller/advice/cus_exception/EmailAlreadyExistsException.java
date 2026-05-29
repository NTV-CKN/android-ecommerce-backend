package com.example.pkcn.controller.advice.cus_exception;

public class EmailAlreadyExistsException extends Exception {
    public EmailAlreadyExistsException(String msg) {
        super(msg);
    }
}
