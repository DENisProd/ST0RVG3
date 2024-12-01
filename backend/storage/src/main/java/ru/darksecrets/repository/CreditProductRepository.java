package ru.darksecrets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.darksecrets.models.CreditProduct;

@Repository
public interface CreditProductRepository extends JpaRepository<CreditProduct, Long> {
}
