package com.example.PaymentService.Repository;

import com.example.PaymentService.Model.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<CustomerModel,Long> {
}
