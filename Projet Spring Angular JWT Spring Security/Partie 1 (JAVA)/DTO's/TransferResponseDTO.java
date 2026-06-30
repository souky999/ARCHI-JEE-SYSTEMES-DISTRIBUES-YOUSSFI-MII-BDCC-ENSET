package com.example.bankapp.dtos;

// DTO spécifique pour la réponse d'un virement
public record TransferResponseDTO(
        String fromAccountId, 
        String toAccountId, 
        double amount, 
        AccountOperationDTO debitOperation, 
        AccountOperationDTO creditOperation
) {}