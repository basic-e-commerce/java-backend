package com.example.ecommercebasic.exception;

public class UnAuthorizedException extends RuntimeException {
    public UnAuthorizedException(String string) {
        super(string);
    }
}
