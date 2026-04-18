package com.example.PaymentService.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ACCOUNTBALANCE")
public class AccountBalance {

    @Id
    @Column(name="ACCOUNTID")
    private Long accountId;

    @Column(name = "BALANCE")
    private Double balance;

    @Column (name = "CIFID")
    private Long cifId;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Long getCifId() {
        return cifId;
    }

    public void setCifId(Long cifId) {
        this.cifId = cifId;
    }
}
