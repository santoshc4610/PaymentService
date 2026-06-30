package com.example.PaymentService.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name ="LOANSCHEMESANDRATES")
public class LoanSchemesAndRates {

    @Id
    @Column(name="LOANSCHEMEID")
    String loanSchemeId;

    @Column(name="LOANSCHEMEDESCRIPTION")
    String loanSchemeDescription;

    @Column(name="INTERESTRATE")
    double interestRate;

    public String getLoanSchemeId() {
        return loanSchemeId;
    }

    public String getLoanSchemeDescription() {
        return loanSchemeDescription;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setLoanSchemeId(String loanSchemeId) {
        this.loanSchemeId = loanSchemeId;
    }

    public void setLoanSchemeDescription(String loanSchemeDescription) {
        this.loanSchemeDescription = loanSchemeDescription;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
}
