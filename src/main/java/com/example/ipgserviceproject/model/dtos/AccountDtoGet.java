package com.example.ipgserviceproject.model.dtos;

import com.example.ipgserviceproject.model.entity.Account;
import com.example.ipgserviceproject.model.enums.AccountStatus;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class AccountDtoGet {
    private long cardId;
    private long ownerNationalId;
    private long balance;
    private AccountStatus status;
    private Timestamp createdAt;

    public AccountDtoGet(Account account) {
        this.cardId = account.getCardId();
        this.ownerNationalId = account.getOwnerNationalId();
        this.balance = account.getBalance();
        this.status = account.getStatus();
        this.createdAt = account.getCreatedAt();
    }
}
