package com.example.PaymentService.Service;

import com.example.PaymentService.Dto.AccountDetails;
import com.example.PaymentService.Model.AccountBalance;
import com.example.PaymentService.Repository.AccountBalanceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountBalanceUpdate {

    @Autowired
    private AccountBalanceRepo accountBalanceRepo;

    public void updateAccountBalance(AccountDetails accountDetails, Double amount)
    {
        AccountBalance sender = accountBalanceRepo.findById(accountDetails.getSenderAccount()).orElseThrow(() -> new RuntimeException("Sender Account is invalid"));
        AccountBalance receiver = accountBalanceRepo.findById(accountDetails.getReceiverAccount()).orElseThrow(() -> new RuntimeException("Receiver Account is invalid"));

        if(sender.getBalance() < amount)
        {
            throw new RuntimeException("Insufficient balance");
        }

        sender.setBalance(sender.getBalance()-amount);
        receiver.setBalance(receiver.getBalance()+amount);
        accountBalanceRepo.save(receiver);
        accountBalanceRepo.save(sender);
    }
}
