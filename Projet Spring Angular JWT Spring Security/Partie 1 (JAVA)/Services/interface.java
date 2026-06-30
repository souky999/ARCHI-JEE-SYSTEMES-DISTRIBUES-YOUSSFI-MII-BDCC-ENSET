package com.example.bankapp.services;

import com.example.bankapp.dtos.*;
import java.util.List;

public interface BankAccountService {
    
    // Gestion des clients et comptes
    CustomerDTO addNewCustomer(CustomerDTO customerDTO);
    BankAccountDTO createBankAccount(CreateBankAccountRequest request);
    BankAccountDTO getBankAccount(String accountId);
    List<BankAccountDTO> getBankAccountsByCustomer(Long customerId);
    
    // Opérations bancaires
    AccountOperationDTO debit(AccountOperationRequest request);
    AccountOperationDTO credit(AccountOperationRequest request);
    TransferResponseDTO transfer(String fromAccountId, String toAccountId, double amount, String description);
    
    // Historique
    List<AccountOperationDTO> getAccountHistory(String accountId);
}