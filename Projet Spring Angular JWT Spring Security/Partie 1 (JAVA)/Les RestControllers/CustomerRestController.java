package com.example.bankapp.controllers;

import com.example.bankapp.dtos.CustomerDTO;
import com.example.bankapp.services.BankAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@AllArgsConstructor
@Tag(name = "Customers", description = "APIs pour la gestion des clients")
public class CustomerRestController {

    private final BankAccountService bankAccountService;

    @GetMapping
    @Operation(summary = "Récupérer la liste de tous les clients")
    public List<CustomerDTO> getAllCustomers() {
        return bankAccountService.getAllCustomers();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un client par son ID")
    public CustomerDTO getCustomerById(@PathVariable Long id) {
        return bankAccountService.getCustomerById(id);
    }

    @PostMapping
    @Operation(summary = "Créer un nouveau client")
    public CustomerDTO createCustomer(@RequestBody @Valid CustomerDTO customerDTO) {
        return bankAccountService.addNewCustomer(customerDTO);
    }
}