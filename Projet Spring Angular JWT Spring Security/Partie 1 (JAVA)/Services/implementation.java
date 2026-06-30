package com.example.bankapp.services.impl;

import com.example.bankapp.dtos.*;
import com.example.bankapp.entities.*;
import com.example.bankapp.entities.enums.OperationType;
import com.example.bankapp.exceptions.*;
import com.example.bankapp.mappers.BankMapper;
import com.example.bankapp.repositories.*;
import com.example.bankapp.services.BankAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {

    private final CustomerRepository customerRepository;
    private final BankAccountRepository bankAccountRepository;
    private final AccountOperationRepository accountOperationRepository;
    private final BankMapper bankMapper;

    @Override
    public CustomerDTO addNewCustomer(CustomerDTO customerDTO) {
        log.info("Ajout d'un nouveau client: {}", customerDTO.email());
        Customer customer = Customer.builder()
                .firstName(customerDTO.firstName())
                .lastName(customerDTO.lastName())
                .email(customerDTO.email())
                .build();
        
        Customer savedCustomer = customerRepository.save(customer);
        return bankMapper.fromCustomer(savedCustomer);
    }

    @Override
    public BankAccountDTO createBankAccount(CreateBankAccountRequest request) {
        log.info("Création d'un compte pour le client: {}", request.customerId());
        
        Customer customer = customerRepository.findById(Long.parseLong(request.customerId()))
                .orElseThrow(() -> new CustomerNotFoundException("Client introuvable"));

        // Génération d'un ID de compte unique (ex: BA-8F3A9B2C)
        String accountId = "BA-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        BankAccount bankAccount;
        if (request.accountType().equalsIgnoreCase("CA")) {
            bankAccount = CurrentAccount.builder()
                    .accountId(accountId)
                    .balance(request.initialBalance())
                    .overDraft(request.overDraftOrInterestRate())
                    .customer(customer)
                    .createdAt(LocalDateTime.now())
                    .build();
        } else {
            bankAccount = SavingAccount.builder()
                    .accountId(accountId)
                    .balance(request.initialBalance())
                    .interestRate(request.overDraftOrInterestRate())
                    .customer(customer)
                    .createdAt(LocalDateTime.now())
                    .build();
        }

        BankAccount savedAccount = bankAccountRepository.save(bankAccount);
        return bankMapper.fromBankAccount(savedAccount);
    }

    @Override
    @Transactional(readOnly = true)
    public BankAccountDTO getBankAccount(String accountId) {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new BankAccountNotFoundException("Compte introuvable"));
        return bankMapper.fromBankAccount(bankAccount);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BankAccountDTO> getBankAccountsByCustomer(Long customerId) {
        List<BankAccount> accounts = bankAccountRepository.findByCustomer_Id(customerId);
        return accounts.stream().map(bankMapper::fromBankAccount).toList();
    }

    @Override
    public AccountOperationDTO debit(AccountOperationRequest request) {
        BankAccount bankAccount = getBankAccountEntity(request.accountId());
        
        // Vérification du solde selon le type de compte
        if (bankAccount instanceof CurrentAccount currentAccount) {
            if (bankAccount.getBalance() + currentAccount.getOverDraft() < request.amount()) {
                throw new BalanceNotSufficientException("Solde insuffisant (Découvert autorisé dépassé)");
            }
        } else {
            if (bankAccount.getBalance() < request.amount()) {
                throw new BalanceNotSufficientException("Solde insuffisant");
            }
        }

        bankAccount.setBalance(bankAccount.getBalance() - request.amount());
        
        AccountOperation operation = AccountOperation.builder()
                .type(OperationType.DEBIT)
                .amount(request.amount())
                .description(request.description())
                .operationDate(LocalDateTime.now())
                .bankAccount(bankAccount)
                .build();
                
        accountOperationRepository.save(operation);
        bankAccountRepository.save(bankAccount); // Mise à jour du solde
        
        return bankMapper.fromAccountOperation(operation);
    }

    @Override
    public AccountOperationDTO credit(AccountOperationRequest request) {
        BankAccount bankAccount = getBankAccountEntity(request.accountId());
        bankAccount.setBalance(bankAccount.getBalance() + request.amount());
        
        AccountOperation operation = AccountOperation.builder()
                .type(OperationType.CREDIT)
                .amount(request.amount())
                .description(request.description())
                .operationDate(LocalDateTime.now())
                .bankAccount(bankAccount)
                .build();
                
        accountOperationRepository.save(operation);
        bankAccountRepository.save(bankAccount);
        
        return bankMapper.fromAccountOperation(operation);
    }

    @Override
    public TransferResponseDTO transfer(String fromAccountId, String toAccountId, double amount, String description) {
        log.info("Virement de {} vers {} d'un montant de {}", fromAccountId, toAccountId, amount);
        
        // Le @Transactional de la classe garantit que si le crédit échoue, le débit est annulé (rollback)
        AccountOperationDTO debitOp = debit(new AccountOperationRequest(fromAccountId, amount, "Virement vers " + toAccountId + " - " + description));
        AccountOperationDTO creditOp = credit(new AccountOperationRequest(toAccountId, amount, "Virement de " + fromAccountId + " - " + description));
        
        return new TransferResponseDTO(fromAccountId, toAccountId, amount, debitOp, creditOp);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountOperationDTO> getAccountHistory(String accountId) {
        List<AccountOperation> operations = accountOperationRepository.findByBankAccount_AccountIdOrderByOperationDateDesc(accountId);
        return operations.stream().map(bankMapper::fromAccountOperation).toList();
    }

    // Méthode utilitaire privée pour récupérer l'entité ou lever une exception
    private BankAccount getBankAccountEntity(String accountId) {
        return bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new BankAccountNotFoundException("Compte introuvable"));
    }
}