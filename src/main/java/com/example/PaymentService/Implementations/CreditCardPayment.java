package com.example.PaymentService.Implementations;

import com.example.PaymentService.Interfaces.Balance;
import com.example.PaymentService.Interfaces.Payment;
import com.example.PaymentService.Interfaces.Validator;
import org.springframework.stereotype.Component;

@Component("creditCard")
public class CreditCardPayment implements Payment {

    @Override
    public boolean pay(Long amount)
    {
        return true;
    }

    public Long getBalance(Long amount)
    {
        int balance = 10000;
        return balance -amount;
    }
}
