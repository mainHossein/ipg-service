package com.example.ipgserviceproject.service;

import com.example.ipgserviceproject.model.entity.Account;
import com.example.ipgserviceproject.model.entity.Transaction;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface AccountService {
    Account getAccount(long cardId);

    Account getReferencedAccount(long cardId);

    Set<Transaction> getAccountTransactions(long cardId);

    Account postAccount(long nationalId);

    Boolean accountIsOpen(long cardId);

    Boolean accountValid(long cardId);

    Boolean checkBalance(long cardId, long fee);

    void decreaseBalance(long cardId, long fee);

    void increaseBalance(long cardId, long fee);

    void increaseShopBalance(long fee);

    void decreaseShopBalance(long fee);

    void closeAccount(long cardId);

    void openAccount(long cardId);

    void deleteAccount(long cardId);
}