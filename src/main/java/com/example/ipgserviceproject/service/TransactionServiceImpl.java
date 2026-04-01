package com.example.ipgserviceproject.service;

import com.example.ipgserviceproject.model.dtos.TransactionDtoPost;
import com.example.ipgserviceproject.model.entity.Transaction;
import com.example.ipgserviceproject.model.enums.TransactionStatus;
import com.example.ipgserviceproject.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    @Override
    public Transaction getTransaction(long transactionId) {
        return transactionRepository.findById(transactionId).orElse(null);
    }

    @Override
    public Boolean transactionExists(long transactionId) {
        return transactionRepository.existsByTransactionId(transactionId);
    }

    @Override
    public Transaction saveTransaction(TransactionDtoPost transactionDtoPost) {
        Transaction transaction = new Transaction();
        transaction.setAccount(transactionDtoPost.getAccount());
        transaction.setFee(transactionDtoPost.getFee());
        transaction.setStatus(transactionDtoPost.getStatus());
        return transactionRepository.save(transaction);
    }

    @Override
    public void failTransaction(long transactionId) {
        Transaction transaction = transactionRepository.getReferenceById(transactionId);
        transaction.setStatus(TransactionStatus.FAILED);
        transactionRepository.save(transaction);
    }
}
