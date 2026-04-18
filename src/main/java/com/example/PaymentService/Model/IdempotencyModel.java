package com.example.PaymentService.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name="IDEMPOTENCY")
public class IdempotencyModel {

    @Id
    @Column(name="IDEMPOTENCYKEY")
    private String idempotencyKey;
    @Column(name = "PAYMENTID")
    private Long paymentId;
    @Column(name = "STATUS")
    private String status;
    @Column(name ="CREATEDAT")
    private LocalDateTime createdAt;
    @Column(name = "UPDATEDAT")
    private LocalDateTime updatedAt;
    @Column(name = "LOCKEDAT")
    private LocalDateTime lockedAt;
    @Column(name = "COMMENTS")
    private String Comments;

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getLockedAt() {
        return lockedAt;
    }

    public void setLockedAt(LocalDateTime lockedAt) {
        this.lockedAt = lockedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }
}
