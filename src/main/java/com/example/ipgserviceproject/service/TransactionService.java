package com.example.ipgserviceproject.service;

import com.example.ipgserviceproject.model.entity.Transaction;
import com.example.ipgserviceproject.model.dtos.TransactionDtoPost;
import org.springframework.stereotype.Service;

@Service
public interface TransactionService {
    Transaction getTransaction(long transactionId);

    Boolean transactionExists(long transactionId);

    Transaction saveTransaction(TransactionDtoPost transactionDtoPost);

    void failTransaction(long transactionId);
}
