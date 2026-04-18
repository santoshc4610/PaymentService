package com.example.PaymentService.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TRANSACTIONTYPE")
public class TransactionType {

    @Id
    @Column(name="TRANSACTIONCODE")
    private String transactionCode;

    @Column(name = "DESCRIPTION")
    private String description;

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
