package com.example.ipgserviceproject.model.mapper;

import com.example.ipgserviceproject.model.dtos.TransactionDtoPost;
import com.example.ipgserviceproject.model.entity.Account;
import com.example.ipgserviceproject.model.entity.Transaction;
import com.example.ipgserviceproject.model.output.AccountResultObject;
import com.example.ipgserviceproject.model.output.ResultObject;
import com.example.ipgserviceproject.model.output.TransactionResultObject;
import com.example.ipgserviceproject.model.output.TransactionsResultObject;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public interface Mapper {
    AccountResultObject resultObjectToAccount(ResultObject resultObject, Account account);

    TransactionsResultObject resultObjectToTransactions(ResultObject resultObject, Set<Transaction> transactions);

    TransactionResultObject resultObjectToTransaction(ResultObject resultObject, Transaction transaction);

    Transaction dtoPostToTransaction(TransactionDtoPost transactionDtoPost);
}