package com.example.PaymentService.Service;

import com.example.PaymentService.Dto.RequestMessage;
import com.example.PaymentService.Model.*;
import com.example.PaymentService.Repository.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetrySynchronizationManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public  class PaymentHelper {

    private static final Logger log = LoggerFactory.getLogger(PaymentHelper.class);

    private final PaymentRepo paymentRepo;

    private final TransactionTypeRepo transactionTypeRepo;

    private final CustomerRepo customerRepo;

    private final ReceiverRepo receiverRepo;
    private final PaymentRetryProducer paymentRetryProducer;
    private final IdempotencyService idempotencyService;
    private final AccountBalanceUpdate accountBalanceUpdate;


    public PaymentHelper(PaymentRepo paymentRepo,TransactionTypeRepo transactionTypeRepo,
                         CustomerRepo customerRepo,ReceiverRepo receiverRepo, IdempotencyService idempotencyService,PaymentRetryProducer paymentRetryProducer
                        ,AccountBalanceUpdate accountBalanceUpdate )
    {
        this.paymentRepo = paymentRepo;
        this.customerRepo = customerRepo;
        this.receiverRepo = receiverRepo;
        this.transactionTypeRepo =transactionTypeRepo;
        this.idempotencyService = idempotencyService;
        this.paymentRetryProducer =paymentRetryProducer;
        this.accountBalanceUpdate =accountBalanceUpdate;
    }

    @Transactional
    public PaymentModel savePaymentInfo(RequestMessage requestMessage) {
        String key = requestMessage.getIdempotencyKey();
        RetryContext context = RetrySynchronizationManager.getContext();
        int retryCount =0;
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Idempotency key is required");
        }
        IdempotencyModel record = new IdempotencyModel();
        record.setIdempotencyKey(key);
        record.setStatus("PROCESSING");
        record.setLockedAt(LocalDateTime.now());
        boolean isOwner;
        try {
        isOwner = idempotencyService.attemptInsert(record);
        }
        catch (Exception ex) {
            System.out.println("Duplicate detected for key: " + record.getIdempotencyKey());
            isOwner = false;
        }
        if(!isOwner)
        {
            IdempotencyModel existing = idempotencyService.fetchAndRepairIfStale(key);
            if(existing ==null)
            {
                throw new RuntimeException("Idempotency Record is missing or not committed");
            }

            if(existing.getStatus().equalsIgnoreCase("COMPLETED"))
            {
                if (existing.getPaymentId() == null) {
                    throw new RuntimeException("Payment marked completed but no paymentId found");
                }
               return paymentRepo.findById(existing.getPaymentId()).orElseThrow(() -> new RuntimeException("Payment not found for completed idempotency key"));
            }

            if(existing.getStatus().equalsIgnoreCase("PROCESSING"))
            {
                throw new RuntimeException("Payment is in processing , retry later ");
            }

            throw new RuntimeException("Request is marked Failed, will be recovered");
        }
        try {
            PaymentModel payment = executePayment(requestMessage);
            accountBalanceUpdate.updateAccountBalance(requestMessage.getAccountDetails(),requestMessage.getTransactionAmount());

            TransactionSynchronizationManager.registerSynchronization(
                    new TransactionSynchronization() {
                        @Override
                        public void afterCommit() {

                            try {
                                idempotencyService.markAsCompleted(key, payment.getPaymentIdentifier());
                            }
                            catch (Exception ex)
                            {
                                log.error("Failed to mark idempotency as completed for key: {}", key, ex);
                            }
                        }
                }

            );
            return payment;
        }
        catch (Exception ex){

            if(isSystemFailure(ex))
            {
                PaymentRecoveryModel paymentRecoveryModel = new PaymentRecoveryModel();
                paymentRecoveryModel.setPayload(String.valueOf(requestMessage));
                paymentRecoveryModel.setIdempotencyKey(requestMessage.getIdempotencyKey());
                paymentRecoveryModel.setReceivedAt(LocalDateTime.now());
                if(context!=null)
                    retryCount = context.getRetryCount()+1;
                if(retryCount==3) {
                    paymentRetryProducer.sendRetryToTopic(paymentRecoveryModel);
                    idempotencyService.markForRetry(key);
                }
            }
            idempotencyService.markAsFailed(key,ex);
            throw ex;
        }
    }

    private PaymentModel executePayment(RequestMessage requestMessage)
    {
        CustomerModel customerModel = customerRepo.findById(requestMessage.getSenderId()).orElseThrow(() -> new RuntimeException("Sender not found"));
        ReceiverModel receiverModel = receiverRepo.findById(requestMessage.getReceiverId()).orElseThrow(() -> new RuntimeException("Receiver Not found"));
        TransactionType transactionType = transactionTypeRepo.findById(requestMessage.getTransactionCode()).orElseThrow(() -> new RuntimeException("Transaction type is not found"));
        PaymentModel payment = new PaymentModel();
        payment.setPaymentStatus(requestMessage.getPaymentStatus());
        payment.setTransactionAmount(requestMessage.getTransactionAmount());
        payment.setIdempotencyKey(requestMessage.getIdempotencyKey());
        payment.setTransactionType(transactionType);
        payment.setSender(customerModel);
        payment.setReceiver(receiverModel);

        try {
            return paymentRepo.saveAndFlush(payment);
        }
        catch (DataIntegrityViolationException ex)
        {
            return paymentRepo.findByIdempotencyKey(payment.getIdempotencyKey())
                    .orElseThrow(() -> new RuntimeException("Payment exists but unable to find"));
        }
    }

    private boolean isSystemFailure(Exception ex)
    {
        Throwable root = ex;

        while (root.getCause() != null) {
            root = root.getCause();
        }
        return (
                ex instanceof java.sql.SQLTransientException ||
            ex instanceof org.springframework.dao.TransientDataAccessException ||
            ex instanceof java.net.SocketTimeoutException ||
            ex instanceof java.util.concurrent.TimeoutException ||
            root.getMessage().contains("ORA-00054") ||   // resource busy
            root.getMessage().contains("ORA-01013") ||   // timeout
            root.getMessage().contains("ORA-00060")
                );
    }
}
