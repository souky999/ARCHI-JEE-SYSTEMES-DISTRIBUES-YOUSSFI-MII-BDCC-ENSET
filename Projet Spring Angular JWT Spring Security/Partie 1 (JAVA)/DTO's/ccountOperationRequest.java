package com.example.bankapp.dtos;

// DTO pour les requêtes d'opérations (retrait, versement)
public record AccountOperationRequest(
        String accountId, 
        double amount, 
        String description
) {}