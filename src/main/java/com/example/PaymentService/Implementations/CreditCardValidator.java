package com.example.PaymentService.Implementations;

import com.example.PaymentService.Interfaces.Validator;

public class CreditCardValidator implements Validator {

    private final Validator validator;

    public CreditCardValidator(Validator validator)
    {
        this.validator = validator;
    }

    public boolean validate(Long amount)
    {
        if(amount > 50000) return false ;
        return validator.validate(amount);
    }
}
