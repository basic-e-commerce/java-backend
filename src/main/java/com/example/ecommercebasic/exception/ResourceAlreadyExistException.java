package com.example.ecommercebasic.exception;

public class ResourceAlreadyExistException extends RuntimeException {
    public ResourceAlreadyExistException(String string) {
        super(string);
    }
}
