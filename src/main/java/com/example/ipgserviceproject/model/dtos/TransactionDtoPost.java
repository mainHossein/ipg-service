package com.example.ipgserviceproject.model.dtos;

import com.example.ipgserviceproject.model.entity.Account;
import com.example.ipgserviceproject.model.entity.Transaction;
import com.example.ipgserviceproject.model.enums.TransactionStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TransactionDtoPost {
    private Account account;
    private long fee;
    private TransactionStatus status;
}
