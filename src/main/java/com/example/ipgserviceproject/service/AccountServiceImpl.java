package com.example.ipgserviceproject.service;

import com.example.ipgserviceproject.model.entity.Account;
import com.example.ipgserviceproject.model.enums.AccountStatus;
import com.example.ipgserviceproject.model.entity.Transaction;
import com.example.ipgserviceproject.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    @Value("${shop.cardId}")
    private long shopCardId;

    @Override
    public Account getAccount(long cardId) {
        return accountRepository.findById(cardId).orElse(null);
    }

    @Transactional
    @Override
    public Account getReferencedAccount(long cardId) {
        return accountRepository.getReferenceById(cardId);
    }

    @Transactional
    @Override
    public Set<Transaction> getAccountTransactions(long cardId) {
        Account referencedAccount = accountRepository.getReferenceById(cardId);
        Hibernate.initialize(referencedAccount.getTransactions());
        return referencedAccount.getTransactions();
    }


    @Override
    public Account postAccount(long nationalId) {
        Account account = new Account();
        account.setOwnerNationalId(nationalId);
        account.setStatus(AccountStatus.OPEN);
        return accountRepository.save(account);
    }

    @Override
    public Boolean accountIsOpen(long cardId) {
        String accountStatus = accountRepository.accountStatus(cardId);
        boolean b = Objects.equals(accountStatus, "OPEN");
        return b;
    }

    @Override
    public Boolean accountValid(long cardId) {
        return accountRepository.existsByCardId(cardId);
    }

    @Override
    public Boolean checkBalance(long cardId, long fee) {
        long balance = accountRepository.getBalance(cardId);
        return balance > fee;
    }

    @Override
    public void decreaseBalance(long cardId, long fee) {
        long newBalance = accountRepository.getBalance(cardId) - fee;
        Account account = accountRepository.getReferenceById(cardId);
        account.setBalance(newBalance);
        accountRepository.save(account);
    }

    @Override
    public void increaseBalance(long cardId, long fee) {
        long newBalance = accountRepository.getBalance(cardId) + fee;
        Account account = accountRepository.getReferenceById(cardId);
        account.setBalance(newBalance);
        accountRepository.save(account);
    }

    @Override
    public void increaseShopBalance(long fee) {
        long newBalance = accountRepository.getBalance(shopCardId) + fee;
        Account account = accountRepository.getReferenceById(shopCardId);
        account.setBalance(newBalance);
        accountRepository.save(account);
    }

    @Override
    public void decreaseShopBalance(long fee) {
        long newBalance = accountRepository.getBalance(shopCardId) - fee;
        Account account = accountRepository.getReferenceById(shopCardId);
        account.setBalance(newBalance);
        accountRepository.save(account);
    }

    @Transactional
    @Override
    public void closeAccount(long cardId) {
        Account account = accountRepository.getReferenceById(cardId);
        account.setStatus(AccountStatus.CLOSE);
        accountRepository.save(account);
    }

    @Transactional
    @Override
    public void openAccount(long cardId) {
        Account account = accountRepository.getReferenceById(cardId);
        account.setStatus(AccountStatus.OPEN);
        accountRepository.save(account);
    }

    @Override
    public void deleteAccount(long cardId) {
        accountRepository.deleteById(cardId);
    }
}
