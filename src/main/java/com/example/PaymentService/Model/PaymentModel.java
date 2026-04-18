package com.example.PaymentService.Model;

import jakarta.persistence.*;

@Entity
@Table(name="PAYMENTINFO")
public class PaymentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "paymentId_seq_gen")
    @SequenceGenerator(
            name = "paymentId_seq_gen",
            sequenceName = "PAYMENTID_SEQ",
            allocationSize = 1
    )
    @Column(name = "PAYMENTID")
    private Long PaymentIdentifier;

    @Column(name = "PAYMENTSTATUS")
    private String paymentStatus;

    @Column(name = "TRANSACTIONAMOUNT")
    private Double transactionAmount;

     @ManyToOne
    @JoinColumn(name = "SENDERID")
    private CustomerModel sender;

    @ManyToOne
    @JoinColumn(name = "RECEIVERID")
    private ReceiverModel receiver;

    @Column(name = "IDEMPOTENCYKEY", unique = true)
    private String idempotencyKey;

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }

    public TransactionType getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(TransactionType transactionCode) {
        this.transactionCode = transactionCode;
    }

    public ReceiverModel getReceiver() {
        return receiver;
    }

    public void setReceiver(ReceiverModel receiver) {
        this.receiver = receiver;
    }

    @OneToOne
    @JoinColumn(name = "TRANSACTIONCODE")
    private TransactionType transactionCode;

    public TransactionType getTransactionType() {
        return transactionCode;
    }

    public void setTransactionType(TransactionType transactionCode) {
        this.transactionCode = transactionCode;
    }

    public CustomerModel getSender() {
        return sender;
    }

    public void setSender(CustomerModel sender) {
        this.sender = sender;
    }



    public Long getPaymentIdentifier() {
        return PaymentIdentifier;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public void setPaymentIdentifier(Long paymentIdentifier) {
        PaymentIdentifier = paymentIdentifier;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
