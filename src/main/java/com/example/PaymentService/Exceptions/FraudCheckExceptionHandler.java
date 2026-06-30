package com.example.PaymentService.Exceptions;

public class FraudCheckExceptionHandler extends RuntimeException{

    public FraudCheckExceptionHandler(String message)
    {
        super(message);
    }
}
