package com.example.bankapp.controllers;

import com.example.bankapp.dtos.*;
import com.example.bankapp.services.BankAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
@Tag(name = "Bank Accounts", description = "APIs pour la gestion des comptes et opérations")
public class BankAccountRestController {

    private final BankAccountService bankAccountService;

    @GetMapping("/{accountId}")
    @Operation(summary = "Consulter les détails d'un compte")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) {
        return bankAccountService.getBankAccount(accountId);
    }

    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Consulter tous les comptes d'un client")
    public List<BankAccountDTO> getBankAccountsByCustomer(@PathVariable Long customerId) {
        return bankAccountService.getBankAccountsByCustomer(customerId);
    }

    @PostMapping
    @Operation(summary = "Créer un nouveau compte bancaire")
    public BankAccountDTO createBankAccount(@RequestBody @Valid CreateBankAccountRequest request) {
        return bankAccountService.createBankAccount(request);
    }

    @PostMapping("/debit")
    @Operation(summary = "Effectuer un retrait")
    public AccountOperationDTO debit(@RequestBody @Valid AccountOperationRequest request) {
        return bankAccountService.debit(request);
    }

    @PostMapping("/credit")
    @Operation(summary = "Effectuer un versement")
    public AccountOperationDTO credit(@RequestBody @Valid AccountOperationRequest request) {
        return bankAccountService.credit(request);
    }

    @PostMapping("/transfer")
    @Operation(summary = "Effectuer un virement entre deux comptes")
    public TransferResponseDTO transfer(@RequestBody @Valid TransferRequestDTO request) {
        return bankAccountService.transfer(
                request.fromAccountId(), 
                request.toAccountId(), 
                request.amount(), 
                request.description()
        );
    }

    @GetMapping("/{accountId}/operations")
    @Operation(summary = "Consulter l'historique des opérations d'un compte")
    public List<AccountOperationDTO> getAccountHistory(@PathVariable String accountId) {
        return bankAccountService.getAccountHistory(accountId);
    }
}