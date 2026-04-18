package com.example.PaymentService.Repository;

import com.example.PaymentService.Model.PaymentRecoveryModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRecoveryRepo extends JpaRepository<PaymentRecoveryModel,Long> {
}
