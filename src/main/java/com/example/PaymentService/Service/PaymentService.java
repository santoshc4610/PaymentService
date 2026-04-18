package com.example.PaymentService.Service;

import com.example.PaymentService.Dto.RequestMessage;
import com.example.PaymentService.Model.PaymentModel;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PaymentService {

    PaymentModel getTransactionStatusById(Long id);

    PaymentModel saveTransaction(RequestMessage payment);
}
