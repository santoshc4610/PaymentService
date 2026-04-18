package com.example.PaymentService.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "RECEIVER")
public class ReceiverModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "receiverId_seq_gen")
    @SequenceGenerator(
            name = "receiverId_seq_gen",
            sequenceName = "RECEIVERID_SEQ",
            allocationSize = 1
    )
    @Column(name = "RECEIVERID")
    private Long receiverId;

    @Column(name = "RECEIVERNAME")
    private String receiverName;

    @Column(name = "RECEIVERMOB")
    private String receiverMob;

    @Column(name = "CIFID")
    private String cifId;

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverID) {
        this.receiverId = receiverID;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverMob() {
        return receiverMob;
    }

    public void setReceiverMob(String receiverMob) {
        this.receiverMob = receiverMob;
    }

    public String getCifId() {
        return cifId;
    }

    public void setCifId(String cifId) {
        this.cifId = cifId;
    }
}
