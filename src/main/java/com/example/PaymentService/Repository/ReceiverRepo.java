package com.example.PaymentService.Repository;

import com.example.PaymentService.Model.ReceiverModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiverRepo extends JpaRepository<ReceiverModel,Long> {
}
