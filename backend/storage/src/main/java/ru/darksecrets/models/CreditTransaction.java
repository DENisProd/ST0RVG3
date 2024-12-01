package ru.darksecrets.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Builder
public class CreditTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long TransactionID;

    @ManyToOne
    @JoinColumn(name = "CustomerID", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "CreditProductID", nullable = false)
    private CreditAgreement creditAgreement;

    @ManyToOne
    @JoinColumn(name = "transactionTypeId", nullable = false)
    private TransactionType transactionType;

    private LocalDate transactionDate;
    private Double transactionAmount;
}
