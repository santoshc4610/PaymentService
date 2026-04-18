package com.example.PaymentService.Repository;

import com.example.PaymentService.Model.PaymentModel;
import com.example.PaymentService.Projections.CustomerSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//This is a projection
@Repository
public interface PaymentRepo extends JpaRepository<PaymentModel,Long> {

    List<PaymentModel> findByPaymentStatusIgnoreCase(String status);

    List<PaymentModel> findByTransactionCodeTransactionCodeAndTransactionAmountGreaterThan(String code,Double amount);

    @Query(""" 
            SELECT c.customerName as customerName,
            c.customerMob as customerMob,
            pi.transactionAmount as transactionAmount,
            tt.description as description
            from PaymentModel pi
            Join pi.sender c
            join pi.transactionCode tt
            where tt.transactionCode =:code
            """)

    List<CustomerSummary> getCustomerSummaryByTransactionCode(@Param("code") String code);

    Optional<PaymentModel> findByIdempotencyKey(String key);
}
