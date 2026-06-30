package com.example.PaymentService.Service;

import com.example.PaymentService.Dto.RequestMessage;
import com.example.PaymentService.Model.PaymentModel;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class PaymentRetryService {


    private final PaymentWebService paymentWebService;

    public PaymentRetryService(PaymentWebService paymentWebService)
    {
        this.paymentWebService = paymentWebService;
    }

    @Retryable(
            retryFor = {
                    CannotAcquireLockException.class,
                    PessimisticLockingFailureException.class,
                    QueryTimeoutException.class,
                    CannotGetJdbcConnectionException.class
            },
            maxAttempts = 3,
            backoff = @Backoff(delay = 500)
    )
    public PaymentModel invokePaymentWebService(RequestMessage requestModel){
        return paymentWebService.saveTransaction(requestModel);
    }
}
