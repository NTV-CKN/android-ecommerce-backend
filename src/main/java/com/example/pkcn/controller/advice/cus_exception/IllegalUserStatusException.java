package com.example.pkcn.controller.advice.cus_exception;

public class IllegalUserStatusException extends Exception{
    public IllegalUserStatusException(String msg) {
        super(msg);
    }
}
