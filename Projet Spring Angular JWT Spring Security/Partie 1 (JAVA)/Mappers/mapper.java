package com.example.bankapp.mappers;

import com.example.bankapp.dtos.*;
import com.example.bankapp.entities.*;
import org.springframework.stereotype.Component;

@Component
public class BankMapper {

    public CustomerDTO fromCustomer(Customer customer) {
        return new CustomerDTO(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail()
        );
    }

    public BankAccountDTO fromBankAccount(BankAccount bankAccount) {
        String type = bankAccount instanceof CurrentAccount ? "CA" : "SA";
        Double overDraft = bankAccount instanceof CurrentAccount ca ? ca.getOverDraft() : null;
        Double interestRate = bankAccount instanceof SavingAccount sa ? sa.getInterestRate() : null;

        return new BankAccountDTO(
                bankAccount.getAccountId(),
                bankAccount.getBalance(),
                bankAccount.getCreatedAt(),
                type,
                overDraft,
                interestRate,
                bankAccount.getCustomer().getId()
        );
    }

    public AccountOperationDTO fromAccountOperation(AccountOperation operation) {
        return new AccountOperationDTO(
                operation.getId(),
                operation.getOperationDate(),
                operation.getAmount(),
                operation.getType().name(),
                operation.getDescription()
        );
    }
}