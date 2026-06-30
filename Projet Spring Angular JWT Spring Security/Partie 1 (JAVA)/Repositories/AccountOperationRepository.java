package com.example.bankapp.repositories;

import com.example.bankapp.entities.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {
    List<AccountOperation> findByBankAccount_AccountIdOrderByOperationDateDesc(String accountId);
}