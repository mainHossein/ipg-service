package com.example.ipgserviceproject.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    void existsByCardId() {
        System.out.println(accountRepository.existsByCardId(1000000350));
    }

    @Test
    void getBalance() {
        System.out.println(accountRepository.getBalance(100000037));
    }

    @Test
    void accountStatus() {
        System.out.println(accountRepository.accountStatus(100000037));
    }

    @Test
    void getAccountOwnerNationalId() {
    }
}