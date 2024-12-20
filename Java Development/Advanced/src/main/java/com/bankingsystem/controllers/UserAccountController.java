package com.bankingsystem.controllers;

import com.bankingsystem.Entity.BankAccount;
import com.bankingsystem.Entity.Transaction;
import com.bankingsystem.Entity.User;
import com.bankingsystem.Repositories.UserRepository;
import com.bankingsystem.services.TransactionService;
import com.bankingsystem.services.accountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/account")
public class UserAccountController {
    private final UserRepository userRepository;
    private final TransactionService transactionService;
    private final accountService service;
    @GetMapping("/get-account")
    public ResponseEntity<BankAccount> getAccounts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return new ResponseEntity<>(user.getBankAccount(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add-accounts")
    public String addAccount(@RequestBody BankAccount bankAccount) {
        service.saveAccount(bankAccount);
        return null;
    }

    @PutMapping("/transaction")
    public String transaction(@RequestBody Transaction transaction) {
        transactionService.save(transaction);
        return "transaction successful";
    }


}
