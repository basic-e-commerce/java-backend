package com.example.ecommercebasic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<?> BadRequestException(BadRequestException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<?> NotFoundException(NotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler({ResourceAlreadyExistException.class})
    public ResponseEntity<?> ResourceAlreadyExistException(ResourceAlreadyExistException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler({UnAuthorizedException.class})
    public ResponseEntity<?> UnAuthorizedException(UnAuthorizedException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler({InvalidFormatException.class})
    public ResponseEntity<?> InvalidFormatException(InvalidFormatException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler({TokenExpiredException.class})
    public ResponseEntity<?> TokenExpiredException(TokenExpiredException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
