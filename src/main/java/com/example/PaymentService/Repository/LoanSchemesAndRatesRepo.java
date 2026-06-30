package com.example.PaymentService.Repository;

import com.example.PaymentService.Model.LoanSchemesAndRates;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanSchemesAndRatesRepo extends JpaRepository<LoanSchemesAndRates,String> {
}
