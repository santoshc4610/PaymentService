package com.example.PaymentService.Service;

import com.example.PaymentService.Model.PaymentRecoveryModel;
import com.example.PaymentService.Repository.PaymentRecoveryRepo;
import org.springframework.stereotype.Service;

@Service
public class PaymentRetryProducer {

    private final PaymentRecoveryRepo paymentRecoveryRepo;

    public PaymentRetryProducer(PaymentRecoveryRepo paymentRecoveryRepo)
    {
        this.paymentRecoveryRepo =paymentRecoveryRepo;
    }

    public void sendRetryToTopic(PaymentRecoveryModel paymentRecoveryModel)
    {
        paymentRecoveryRepo.save(paymentRecoveryModel);
    }
}
