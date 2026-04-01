package com.example.ipgserviceproject.model.dtos;

import com.example.ipgserviceproject.model.entity.Transaction;
import com.example.ipgserviceproject.model.enums.TransactionStatus;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class TransactionDtoGet {
    private long transactionId;
    private long cardId;
    private long fee;
    private TransactionStatus status;
    private Timestamp transactionTime;

    public TransactionDtoGet(Transaction transaction) {
        this.transactionId = transaction.getTransactionId();
        this.cardId = transaction.getAccount().getCardId();
        this.fee = transaction.getFee();
        this.status = transaction.getStatus();
        this.transactionTime = transaction.getTransactionTime();
    }
}
