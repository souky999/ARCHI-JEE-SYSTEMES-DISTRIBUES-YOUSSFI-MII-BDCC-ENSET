package com.example.bankapp.dtos;

// DTO pour la création d'un compte
public record CreateBankAccountRequest(
        String customerId, 
        double initialBalance, 
        double overDraftOrInterestRate, 
        String accountType // "CA" ou "SA"
) {}