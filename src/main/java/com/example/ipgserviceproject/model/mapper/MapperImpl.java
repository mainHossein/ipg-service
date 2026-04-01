package com.example.ipgserviceproject.model.mapper;

import com.example.ipgserviceproject.model.dtos.AccountDtoGet;
import com.example.ipgserviceproject.model.dtos.TransactionDtoGet;
import com.example.ipgserviceproject.model.dtos.TransactionDtoPost;
import com.example.ipgserviceproject.model.entity.Account;
import com.example.ipgserviceproject.model.entity.Transaction;
import com.example.ipgserviceproject.model.output.AccountResultObject;
import com.example.ipgserviceproject.model.output.ResultObject;
import com.example.ipgserviceproject.model.output.TransactionResultObject;
import com.example.ipgserviceproject.model.output.TransactionsResultObject;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class MapperImpl implements Mapper {
    @Override
    public AccountResultObject resultObjectToAccount(ResultObject resultObject, Account account) {
        AccountResultObject accountResultObject = new AccountResultObject();
        if (account == null) {
            accountResultObject.setAccount(null);
        } else {
            accountResultObject.setAccount(new AccountDtoGet(account));
        }
        accountResultObject.setMetaData(resultObject.getMetaData());
        return accountResultObject;
    }

    @Override
    public TransactionsResultObject resultObjectToTransactions(ResultObject resultObject, Set<Transaction> transactions) {
        TransactionsResultObject transactionsResultObject = new TransactionsResultObject();
        Set<TransactionDtoGet> transactionDtoGetSet = new HashSet<>();
        if (transactions != null) {
            transactions.forEach(transaction -> {
                TransactionDtoGet transactionDtoGet = new TransactionDtoGet(transaction);
                transactionDtoGetSet.add(transactionDtoGet);
            });
        }
        transactionsResultObject.setTransaction(transactionDtoGetSet);
        transactionsResultObject.setMetaData(resultObject.getMetaData());
        return transactionsResultObject;
    }

    @Override
    public TransactionResultObject resultObjectToTransaction(ResultObject resultObject, Transaction transaction) {
        TransactionResultObject transactionResultObject = new TransactionResultObject();
        if (transaction == null) {
            transactionResultObject.setTransaction(null);
        }
        else {
            transactionResultObject.setTransaction(new TransactionDtoGet(transaction));
        }
        transactionResultObject.setMetaData(resultObject.getMetaData());
        return transactionResultObject;
    }

    @Override
    public Transaction dtoPostToTransaction(TransactionDtoPost transactionDtoPost) {
        Transaction transaction = new Transaction();
        transaction.setAccount(transactionDtoPost.getAccount());
        transaction.setFee(transactionDtoPost.getFee());
        transaction.setStatus(transactionDtoPost.getStatus());
        return transaction;
    }

}
