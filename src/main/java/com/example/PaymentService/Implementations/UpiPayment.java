package com.example.PaymentService.Implementations;

import com.example.PaymentService.Interfaces.Payment;
import org.springframework.stereotype.Component;

@Component("upi")
public class UpiPayment implements Payment {

    @Override
    public boolean pay(Long amount)
    {
        System.out.println("coming inside Upi payment");
        return true;
    }
}
