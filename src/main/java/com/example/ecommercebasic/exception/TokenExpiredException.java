package com.example.ecommercebasic.exception;

public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException(String tryLogin) {
        super(tryLogin);
    }
}
