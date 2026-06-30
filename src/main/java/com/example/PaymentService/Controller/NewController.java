package com.example.PaymentService.Controller;


import com.example.PaymentService.Dto.AccountBalance;
import com.example.PaymentService.Dto.AccountDto;
import com.example.PaymentService.Implementations.CreditCardPayment;
import com.example.PaymentService.Implementations.MaxAmountValidator;
import com.example.PaymentService.Implementations.NoOpValidators;
import com.example.PaymentService.Implementations.ZeroAmountValidator;
import com.example.PaymentService.Interfaces.Payment;
import com.example.PaymentService.Interfaces.Validator;
import com.example.PaymentService.Service.PaymentService;
import com.example.PaymentService.Service.PaymentWebService;
import com.example.PaymentService.Service.PaymentWebServiceLite;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api1")
@RestController
public class NewController {

    @Autowired
    PaymentWebServiceLite paymentWebServiceLite;
    @GetMapping("/getAccount/{customerName}")
    public AccountDto getAccountDetails(@PathVariable String customerName)
    {
        AccountDto accountDto = new AccountDto("MUFG","Santosh");
        return accountDto;
    }

    @PostMapping("/makePayment/{paymentType}/{amount}")
    public boolean makePay(@PathVariable Long amount, @PathVariable String paymentType)
    {
        return paymentWebServiceLite.makePayment(amount,paymentType);
    }


}
