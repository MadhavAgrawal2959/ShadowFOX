package com.bankingsystem.services;

import com.bankingsystem.Entity.BankAccount;
import com.bankingsystem.Entity.User;
import com.bankingsystem.Repositories.AccountRepository;
import com.bankingsystem.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class accountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public void saveAccount(BankAccount bankAccount) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String username = authentication.getName();
            User user = userRepository.findByUsername(username);
            if(user.getBankAccount()!=null)  throw new IllegalStateException("Bank account already exists");
            bankAccount.setOwner(username);
            accountRepository.save(bankAccount);
            user.setBankAccount(bankAccount);
            userRepository.save(user);
        }

    }
}