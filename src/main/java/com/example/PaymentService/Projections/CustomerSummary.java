package com.example.PaymentService.Projections;

public interface CustomerSummary {
    String getCustomerName();
    String getCustomerMob();
    Double getTransactionAmount();
    String getDescription();
}
