package com.example.ipgserviceproject.repository;

import com.example.ipgserviceproject.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    boolean existsByTransactionId(long transactionId);

}
