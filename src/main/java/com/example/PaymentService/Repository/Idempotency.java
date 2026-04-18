package com.example.PaymentService.Repository;

import com.example.PaymentService.Model.IdempotencyModel;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface Idempotency extends JpaRepository<IdempotencyModel,String> {


}
