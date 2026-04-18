package com.example.PaymentService.Model;

import jakarta.persistence.*;

@Entity
@Table(name ="Customers")
public class CustomerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customerId_seq_gen")
    @SequenceGenerator(
            name = "customerId_seq_gen",
            sequenceName = "CUSTOMERID_SEQ",
            allocationSize = 1
    )
    @Column(name = "CUSTOMERID")
    private Long customerId;

    @Column(name = "CUSTOMERNAME")
    private String customerName;
    @Column(name = "CUSTOMEREMAIL")
    private String customerEmail;
    @Column(name = "CUSTOMERMOB")
    private String customerMob;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerMob() {
        return customerMob;
    }

    public void setCustomerMob(String customerMob) {
        this.customerMob = customerMob;
    }
}
