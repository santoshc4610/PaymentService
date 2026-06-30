package com.example.PaymentService.Service;

import com.example.PaymentService.Interfaces.Payment;
import com.example.PaymentService.Interfaces.Validator;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PaymentWebServiceLite {

    private final Map<String,Payment> paymentMap;
    private final Map<String,Validator> validatorMap;
    public PaymentWebServiceLite(Map<String,Payment> paymentMap, Map<String,Validator> validatorMap)
    {
        this.paymentMap = paymentMap;
        this.validatorMap = validatorMap;
    }

    public boolean makePayment(Long amount,String paymentType)
    {
        Payment payment = paymentMap.get(paymentType);
        Validator validator = validatorMap.get(paymentType+"Validator");
        if(validator == null) validator = validatorMap.get("defaultValidator");

        if(!validator.validate(amount)) return false;

        return payment.pay(amount);
    }

}
