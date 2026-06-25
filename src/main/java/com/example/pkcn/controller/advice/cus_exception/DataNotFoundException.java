package com.example.pkcn.controller.advice.cus_exception;

public class DataNotFoundException extends RuntimeException{
    public DataNotFoundException(String msg) {
        super(msg);
    }
}
