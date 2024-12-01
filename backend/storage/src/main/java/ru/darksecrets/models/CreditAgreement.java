package ru.darksecrets.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Builder
public class CreditAgreement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long CreditAgreementID;
    @ManyToOne
    @JoinColumn(name = "CustomerID", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "CreditProductID", nullable = false)
    private CreditProduct creditProduct;

    private LocalDate agreementDate;
    private Double loanAmount;
    private Integer loanTerm;
    private Double interestRate;
}
