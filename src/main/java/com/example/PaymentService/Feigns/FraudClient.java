package com.example.PaymentService.Feigns;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="fraudClient",url="${services.fraud-url}")
public interface FraudClient {
    @GetMapping("/fraud/fraudCheck/{customerId}/{amount}")
    boolean checkFraud(@PathVariable("customerId") Long customerId,@PathVariable("amount") double amount);
}
