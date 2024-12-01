package ru.darksecrets.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Builder
public class CreditProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long CreditProductID;
    public String ProductName;
    public BigDecimal InterestRate;
    public BigDecimal MaxLoanAmount;
    public Integer MinRepaymentTerm;
    public String CollateralRequired;
}
