package com.example.PaymentService.Implementations;

import com.example.PaymentService.Interfaces.Validator;
import org.apache.commons.lang.Validate;

public class MaxAmountValidator implements Validator {

    private final Validator validator;
    public MaxAmountValidator(Validator validator)
    {
        this.validator = validator;
    }

    public boolean validate(Long amount)
    {
        if(!validator.validate(amount)) return false;
        return amount <= 2000;
    }
}
