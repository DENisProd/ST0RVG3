package ru.darksecrets.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long CustomerID;
    public Long CustomerTypeID;
    public String Name;
    public String DateOfBirth = null;
    public String RegistrationDate = null;
    public String TIN;
    public String ContactInfo;
}
