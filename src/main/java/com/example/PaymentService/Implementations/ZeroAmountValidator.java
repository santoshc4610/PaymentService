package com.example.PaymentService.Implementations;

import com.example.PaymentService.Interfaces.Validator;
import org.springframework.stereotype.Component;

public class ZeroAmountValidator implements Validator {

    private final Validator validator;
    public ZeroAmountValidator(Validator validator)
    {
        this.validator = validator;
    }

    public boolean validate(Long amount)
    {
        if(!validator.validate(amount)) return false;
        return amount >= 0;
    }
}
