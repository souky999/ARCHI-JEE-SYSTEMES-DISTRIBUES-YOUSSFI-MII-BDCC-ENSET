package com.example.bankapp.dtos;

import java.time.LocalDateTime;

public record BankAccountDTO(
        String accountId, 
        double balance, 
        LocalDateTime createdAt, 
        String type,         // "CA" ou "SA"
        Double overDraft,    // Null si c'est un compte épargne
        Double interestRate, // Null si c'est un compte courant
        Long customerId
) {}