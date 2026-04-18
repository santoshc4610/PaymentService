package com.example.PaymentService.Repository;

import com.example.PaymentService.Model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionTypeRepo extends JpaRepository<TransactionType,String> {
}
