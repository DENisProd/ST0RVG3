package ru.darksecrets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.darksecrets.models.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
