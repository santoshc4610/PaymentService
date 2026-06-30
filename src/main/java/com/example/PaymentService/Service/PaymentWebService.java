package com.example.PaymentService.Service;

import com.example.PaymentService.Dto.RequestMessage;
import com.example.PaymentService.Exceptions.FraudCheckExceptionHandler;
import com.example.PaymentService.Feigns.FraudClient;
import com.example.PaymentService.Interfaces.Payment;
import com.example.PaymentService.Interfaces.Validator;
import com.example.PaymentService.Model.*;
import com.example.PaymentService.Projections.CustomerSummary;
import com.example.PaymentService.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Service
@Primary
public class PaymentWebService implements PaymentService{

   private final PaymentRepo paymentRepo;
   private final CustomerRepo customerRepo;
   private final PaymentHelper paymentHelper;
   private final LoanSchemesAndRatesRepo loanSchemesAndRatesRepo;
   private final FraudClient fraudClient;
   private final ExecutorService executorService;

    public PaymentWebService(PaymentRepo paymentRepo,
                             CustomerRepo customerRepo,
                             ReceiverRepo receiverRepo,
                             PaymentHelper paymentHelper,
                             LoanSchemesAndRatesRepo loanSchemesAndRatesRepo,
                             FraudClient fraudClient,
                             ExecutorService executorService
                             ) {
        this.paymentRepo = paymentRepo;
        this.customerRepo = customerRepo;
        this.paymentHelper = paymentHelper;
        this.loanSchemesAndRatesRepo =loanSchemesAndRatesRepo;
        this.fraudClient = fraudClient;
        this.executorService = executorService;

    }

    public PaymentModel getTransactionStatusById(Long id)
    {
        return paymentRepo.findById(id).orElseThrow(() -> new RuntimeException("Transaction Not found"));
    }

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class,
            timeout = 5
    )
    public PaymentModel saveTransaction(RequestMessage requestMessage) {
        System.out.println("Account details"+requestMessage.getAccountDetails());
        CompletableFuture<Boolean> fraudFuture=
                CompletableFuture.supplyAsync(
                        () -> fraudClient.checkFraud(requestMessage.getSenderId(),
                                requestMessage.getTransactionAmount()),executorService);

        CompletableFuture.allOf(fraudFuture).join();
        boolean fraudStatus = fraudFuture.join();
        if(!fraudStatus)
            throw new FraudCheckExceptionHandler("Payment is detected as Fraud");
        return paymentHelper.savePaymentInfo(requestMessage);
    }

    public List<PaymentModel> getAllTransactionsByStatus(String status)
    {
        return paymentRepo.findByPaymentStatusIgnoreCase(status);
    }

    public CustomerModel saveCustomers(CustomerModel customerModel)
    {
        return customerRepo.save(customerModel);
    }

    public List<PaymentModel> findByTransactionCode(String code, Double amount)
    {
        return paymentRepo.findByTransactionCodeTransactionCodeAndTransactionAmountGreaterThan(code,amount);
    }

    public List<CustomerSummary> getSummary(String code)
    {
        return paymentRepo.getCustomerSummaryByTransactionCode(code);
    }

    public List<LoanSchemesAndRates> getLoanSchemesAndRates()
    {
            return loanSchemesAndRatesRepo.findAll();
    }

    public LoanSchemesAndRates saveLoanSchemeAndRate(LoanSchemesAndRates loanSchemesAndRates)
    {
        return loanSchemesAndRatesRepo.save(loanSchemesAndRates);
    }

}
