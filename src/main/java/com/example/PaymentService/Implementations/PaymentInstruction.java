package com.example.PaymentService.Implementations;

import com.example.PaymentService.Interfaces.Payment;

public class PaymentInstruction implements Payment {

    @Override
    public boolean pay(Long amount)
    {
        return true;
    }
}
