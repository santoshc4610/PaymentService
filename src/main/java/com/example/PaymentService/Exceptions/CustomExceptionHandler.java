package com.example.PaymentService.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler{

    @ExceptionHandler(FraudCheckExceptionHandler.class)
    public ResponseEntity<?> paymentDetectedFraudException(FraudCheckExceptionHandler ex)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}
