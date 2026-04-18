package com.example.PaymentService.Model;

import com.example.PaymentService.Dto.RequestMessage;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "PAYMENTRECOVERY")
public class PaymentRecoveryModel {

    @Id
    @Column(name = "PAYMENTID")
    private Long paymentID;

    @Column(name = "PAYLOAD", columnDefinition = "TEXT")
    private String payload;

    @Column(name="RECEIVEDAT")
    private LocalDateTime receivedAt;

    @Column(name = "IDEMPOTENCYKEY")
    private String idempotencyKey;

    public Long getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(Long paymentID) {
        this.paymentID = paymentID;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public LocalDateTime getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(LocalDateTime receivedAt) {
        this.receivedAt = receivedAt;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }
}
