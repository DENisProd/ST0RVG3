package ru.darksecrets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.darksecrets.models.CreditAgreement;

@Repository
public interface CreditAgreementRepository extends JpaRepository<CreditAgreement, Long> {
}
