package com.example.pkcn.controller.advice.cus_exception;

public class UserNotExistException extends Exception{
    public UserNotExistException(String msg) {
        super(msg);
    }
}
