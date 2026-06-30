package com.example.PaymentService.Controller;

import com.example.OrderingSystem.Dto.OrderRequest;
import com.example.PaymentService.Dto.RequestMessage;
import com.example.PaymentService.Model.CustomerModel;
import com.example.PaymentService.Model.LoanSchemesAndRates;
import com.example.PaymentService.Model.PaymentModel;
import com.example.PaymentService.Projections.CustomerSummary;
import com.example.PaymentService.Service.PaymentRetryService;
import com.example.PaymentService.Service.PaymentWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentWebService paymentWebService;

    @Autowired
    private PaymentRetryService paymentRetryService;

    @GetMapping("/{id}")
    public PaymentModel getTransactionById(@PathVariable Long id)
    {
        System.out.println("🔥 API HIT with ID: " + id);

        return paymentWebService.getTransactionStatusById(id);
    }
    @PostMapping("postPayment")
    public boolean postPayment(@RequestBody OrderRequest ReqMessage)
    {
        return true;
    }
    @PostMapping("reversePayment")
    public boolean reversePayment(@RequestBody RequestMessage ReqMessage)
    {
        return true;
    }
    @PostMapping("save")
    public ResponseEntity<?> saveTransaction(@RequestBody RequestMessage RequestModel)
    {
        System.out.println("AccountDetails" + RequestModel.getAccountDetails());
        return ResponseEntity.ok(paymentRetryService.invokePaymentWebService(RequestModel));
    }
    @GetMapping("status/{status}")
    public List<PaymentModel> getTransactionsByStatus(@PathVariable String status)
    {
        System.out.println("coming into status query");
        return paymentWebService.getAllTransactionsByStatus(status);
    }
    @PostMapping("customers")
    public CustomerModel saveCustomers(@RequestBody CustomerModel customerModel)
    {
        return paymentWebService.saveCustomers(customerModel);
    }

    @GetMapping("ttype/{code}/{amount}")
    public List<PaymentModel> findByTransactionCode(@PathVariable String code,@PathVariable Double amount)
    {
        return paymentWebService.findByTransactionCode(code,amount);
    }

    @GetMapping("getSummary/{code}")
    public List<CustomerSummary> getSummary(@PathVariable String code)
    {
        return paymentWebService.getSummary(code);
    }

    @GetMapping("getLoanSchemesAndRates")
    @Cacheable(value = "loanSchemes")
    public List<LoanSchemesAndRates> getLoanSchemesAndRates()
    {
        System.out.println("Fetching from DB");
        return paymentWebService.getLoanSchemesAndRates();
    }
    @PostMapping("saveLoanSchemeAndRate")
    @CacheEvict(value = "loadSchemes", allEntries = true)
    public LoanSchemesAndRates saveLoanSchemeAndRate(LoanSchemesAndRates loanSchemesAndRates)
    {
        return paymentWebService.saveLoanSchemeAndRate(loanSchemesAndRates);
    }
}
