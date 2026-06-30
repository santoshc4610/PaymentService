package com.example.PaymentService.Implementations;

import com.example.PaymentService.Interfaces.Validator;

public class NoOpValidators implements Validator {


    public boolean validate(Long amount)
    {
        return true;
    }
}
