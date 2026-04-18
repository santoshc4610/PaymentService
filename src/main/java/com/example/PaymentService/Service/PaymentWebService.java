package com.example.PaymentService.Service;

import com.example.PaymentService.Dto.RequestMessage;
import com.example.PaymentService.Model.CustomerModel;
import com.example.PaymentService.Model.PaymentModel;
import com.example.PaymentService.Model.ReceiverModel;
import com.example.PaymentService.Model.TransactionType;
import com.example.PaymentService.Projections.CustomerSummary;
import com.example.PaymentService.Repository.CustomerRepo;
import com.example.PaymentService.Repository.PaymentRepo;
import com.example.PaymentService.Repository.ReceiverRepo;
import com.example.PaymentService.Repository.TransactionTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import java.util.List;

@Service
@Primary
public class PaymentWebService implements PaymentService{

   private final PaymentRepo paymentRepo;
    private final CustomerRepo customerRepo;
    private final PaymentHelper paymentHelper;

    public PaymentWebService(PaymentRepo paymentRepo,
                             CustomerRepo customerRepo,
                             ReceiverRepo receiverRepo,
                             PaymentHelper paymentHelper
                             ) {
        this.paymentRepo = paymentRepo;
        this.customerRepo = customerRepo;
        this.paymentHelper = paymentHelper;
    }

    public PaymentModel getTransactionStatusById(Long id)
    {
        return paymentRepo.findById(id).orElseThrow(() -> new RuntimeException("Transaction Not found"));
    }

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class
    )
    public PaymentModel saveTransaction(RequestMessage requestMessage) {
        System.out.println("Account details"+requestMessage.getAccountDetails());
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
}
