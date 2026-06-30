package com.example.PaymentService.Dto;

public class AccountDto {

    String accountName;
    String customerName;

    public AccountDto(String accountName, String customerName)
    {
        this.accountName = accountName;
        this.customerName = customerName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
