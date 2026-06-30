package com.example.bankapp.dtos;

import java.time.LocalDateTime;

public record AccountOperationDTO(
        Long id, 
        LocalDateTime operationDate, 
        double amount, 
        String type, 
        String description
) {}