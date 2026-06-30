package com.example.bankapp.repositories;

import com.example.bankapp.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
    // Spring Data génère la requête SQL automatiquement grâce au nom de la méthode
    List<BankAccount> findByCustomer_Id(Long customerId);
}