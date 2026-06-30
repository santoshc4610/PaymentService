package com.example.PaymentService.Controller;

import com.example.PaymentService.Implementations.CreditCardValidator;
import com.example.PaymentService.Implementations.MaxAmountValidator;
import com.example.PaymentService.Implementations.NoOpValidators;
import com.example.PaymentService.Implementations.ZeroAmountValidator;
import com.example.PaymentService.Interfaces.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidatorConfig {

    @Bean("defaultValidator")
    public Validator validator()
    {
        return new ZeroAmountValidator(
                new MaxAmountValidator(
                        new NoOpValidators()
                )
        );

    }

    @Bean("creditCardValidator")
    public Validator creditCardValidator()
    {
        return new ZeroAmountValidator(
                new CreditCardValidator(
                        new NoOpValidators()
                )
        );

    }
}
