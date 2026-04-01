package com.example.ipgserviceproject.controller;

import com.example.ipgserviceproject.model.dtos.*;
import com.example.ipgserviceproject.model.entity.Account;
import com.example.ipgserviceproject.model.entity.Transaction;
import com.example.ipgserviceproject.model.enums.TransactionStatus;
import com.example.ipgserviceproject.model.mapper.Mapper;
import com.example.ipgserviceproject.model.output.*;
import com.example.ipgserviceproject.repository.ResultObjectRepository;
import com.example.ipgserviceproject.service.AccountService;
import com.example.ipgserviceproject.service.TransactionService;
import com.example.ipgserviceproject.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class AccountController {
    private final AccountService accountService;
    private final UserService userService;
    private final TransactionService transactionService;
    private final ResultObjectRepository resultObjectRepository;
    private final Mapper mapper;
    private final HttpStatus httpStatus = HttpStatus.OK;

    private ResultObject result;
    private Client client;
    private Status status;
    private MetaData metaData;
    private Account account;
    private Set<Transaction> transactions;
    private Transaction transaction;
    private TransactionDtoPost transactionDtoPost;

    @GetMapping("/get-account-info/{cardId}")
    public ResponseEntity<AccountResultObject> getAccountInfo(@PathVariable long cardId,
                                                              HttpServletRequest request) {
        result = new ResultObject();
        client = new Client();
        status = new Status();
        metaData = new MetaData();
        account = new Account();
        if (accountService.accountValid(cardId)) {
            account = accountService.getAccount(cardId);
            status.setStatusCode(200);
            status.setMessage("Account found");
        } else {
            account = null;
            status.setStatusCode(404);
            status.setMessage("Account not found");
        }
        return new ResponseEntity<>(mapper.resultObjectToAccount(getResultObject(request), account), httpStatus);
    }

    @GetMapping("/get-account-transactions/{cardId}")
    public ResponseEntity<TransactionsResultObject> getAccountTransactions(@PathVariable long cardId,
                                                                           HttpServletRequest request) {
        result = new ResultObject();
        client = new Client();
        status = new Status();
        metaData = new MetaData();
        transactions = new HashSet<>();
        if (accountService.accountValid(cardId)) {
            transactions = accountService.getAccountTransactions(cardId);
            status.setStatusCode(200);
            status.setMessage("Account found");
        } else {
            transactions = null;
            status.setStatusCode(404);
            status.setMessage("Account not found");
        }
        return new ResponseEntity<>(mapper.resultObjectToTransactions(getResultObject(request), transactions), httpStatus);
    }

    @PostMapping("/post-account")
    public ResponseEntity<AccountResultObject> postAccount(@RequestBody AccountDtoPost accountDtoPost,
                                                           HttpServletRequest request) {

        result = new ResultObject();
        client = new Client();
        status = new Status();
        metaData = new MetaData();
        account = new Account();
        if (userService.nationalIdValid(accountDtoPost.getNationalId())) {
            account = accountService.postAccount(accountDtoPost.getNationalId());
            status.setStatusCode(201);
            status.setMessage("Account created");
        } else {
            account = null;
            status.setStatusCode(404);
            status.setMessage("National id is not valid");
        }
        return new ResponseEntity<>(mapper.resultObjectToAccount(getResultObject(request), account), httpStatus);
    }

    @Transactional
    @PostMapping("/purchase")
    public ResponseEntity<TransactionResultObject> purchase(@RequestBody Purchase purchase,
                                                            HttpServletRequest request) {

        result = new ResultObject();
        client = new Client();
        status = new Status();
        metaData = new MetaData();
        transactionDtoPost = new TransactionDtoPost();
        Transaction savedTransaction;
        transactions = new HashSet<>();
        long fee = purchase.getFee();
        long buyerId = purchase.getBuyerId();

        transactionDtoPost.setFee(fee);

        if (accountService.accountValid(buyerId)) {
            if (accountService.accountIsOpen(buyerId)) {
                if (accountService.checkBalance(buyerId, fee)) {
                    accountService.decreaseBalance(buyerId, fee);
                    accountService.increaseShopBalance(fee);
                    status.setStatusCode(200);
                    status.setMessage("Successful transaction");
                    transactionDtoPost.setAccount(accountService.getReferencedAccount(buyerId));
                    transactionDtoPost.setStatus(TransactionStatus.PASSED);
                    savedTransaction = transactionService.saveTransaction(transactionDtoPost);
                } else {
                    status.setMessage("Not enough balance");
                    status.setStatusCode(400);
                    transactionDtoPost.setAccount(accountService.getReferencedAccount(buyerId));
                    transactionDtoPost.setStatus(TransactionStatus.FAILED);
                    savedTransaction = transactionService.saveTransaction(transactionDtoPost);
                }

            } else {
                status.setMessage("This account is closed");
                status.setStatusCode(400);
                transactionDtoPost.setAccount(accountService.getReferencedAccount(buyerId));
                transactionDtoPost.setStatus(TransactionStatus.FAILED);
                savedTransaction = transactionService.saveTransaction(transactionDtoPost);
            }

        } else {
            status.setMessage("This card number is not valid");
            status.setStatusCode(400);
            transactionDtoPost.setAccount(null);
            transactionDtoPost.setStatus(TransactionStatus.FAILED);
            savedTransaction = null;
        }


        return new ResponseEntity<>(mapper.resultObjectToTransaction(getResultObject(request), savedTransaction), httpStatus);
    }

    @Transactional
    @PostMapping("/rollback-purchase")
    public ResponseEntity<ResultObject> rollbackPurchase(@RequestBody TransactionIdPost transactionIdPost,
                                                         HttpServletRequest request) {

        result = new ResultObject();
        client = new Client();
        status = new Status();
        metaData = new MetaData();


        transaction = new Transaction();

        if (transactionService.transactionExists(transactionIdPost.getTransactionId())) {
            transaction = transactionService.getTransaction(transactionIdPost.getTransactionId());
            long cardId = transaction.getAccount().getCardId();
            if (transaction.getStatus() == TransactionStatus.FAILED) {
                status.setStatusCode(400);
                status.setMessage("This transaction is failed and the money couldn't be reverted");
            } else {
                if (accountService.accountIsOpen(cardId)) {
                    long fee = transaction.getFee();
                    accountService.decreaseShopBalance(fee);
                    accountService.increaseBalance(cardId, fee);
                    transactionService.failTransaction(transaction.getTransactionId());
                    status.setStatusCode(200);
                    status.setMessage("Reverting money is done");
                } else {
                    status.setStatusCode(400);
                    status.setMessage("This account is closed");
                }
            }
        } else {
            status.setStatusCode(404);
            status.setMessage("Transaction not found");

        }
        return new ResponseEntity<>(getResultObject(request), httpStatus);
    }


    @PostMapping("/set-account-status")
    public ResponseEntity<ResultObject> setAccountStatus(@RequestBody AccountStatusModifier accountStatusModifier,
                                                         HttpServletRequest request) {

        result = new ResultObject();
        client = new Client();
        status = new Status();
        metaData = new MetaData();

        long cardId = accountStatusModifier.getCardId();
        String fetchedAccountStatus = accountStatusModifier.getAccountStatus();
        if (accountService.accountValid(cardId)) {
            if (Objects.equals(fetchedAccountStatus, "OPEN")) {
                if (accountService.accountIsOpen(cardId)) {
                    status.setStatusCode(204);
                    status.setMessage("Account is already open");
                } else {
                    accountService.openAccount(cardId);
                    status.setStatusCode(204);
                    status.setMessage("Account successfully is open");
                }
            } else if (Objects.equals(fetchedAccountStatus, "CLOSE")) {
                if (!accountService.accountIsOpen(cardId)) {
                    status.setStatusCode(204);
                    status.setMessage("Account is already close");
                } else {
                    accountService.closeAccount(cardId);
                    status.setStatusCode(204);
                    status.setMessage("Account successfully closed");
                }
            } else {
                status.setStatusCode(400);
                status.setMessage("The account status field should be only 'OPEN' or 'CLOSE'");
            }


        } else {
            status.setStatusCode(404);
            status.setMessage("Account not found");

        }
        return new ResponseEntity<>(getResultObject(request), httpStatus);
    }


    @GetMapping("/close-account/{cardId}")
    public ResponseEntity<ResultObject> closeAccount(@PathVariable long cardId,
                                                     HttpServletRequest request) {

        result = new ResultObject();
        client = new Client();
        status = new Status();
        metaData = new MetaData();

        if (accountService.accountValid(cardId)) {
            if (!accountService.accountIsOpen(cardId)) {
                status.setStatusCode(204);
                status.setMessage("Account is already close");
            } else {
                accountService.closeAccount(cardId);
                status.setStatusCode(204);
                status.setMessage("Account successfully closed");
            }
        } else {
            status.setStatusCode(404);
            status.setMessage("Account not found");

        }
        return new ResponseEntity<>(getResultObject(request), httpStatus);
    }

    @GetMapping("/open-account/{cardId}")
    public ResponseEntity<ResultObject> openAccount(@PathVariable long cardId,
                                                    HttpServletRequest request) {

        result = new ResultObject();
        client = new Client();
        status = new Status();
        metaData = new MetaData();

        if (accountService.accountValid(cardId)) {
            if (accountService.accountIsOpen(cardId)) {
                status.setStatusCode(204);
                status.setMessage("Account is already open");
            } else {
                accountService.openAccount(cardId);
                status.setStatusCode(204);
                status.setMessage("Account successfully opened");
            }
        } else {
            status.setStatusCode(404);
            status.setMessage("Account not found");

        }
        return new ResponseEntity<>(getResultObject(request), httpStatus);
    }


    private ResultObject getResultObject(HttpServletRequest request) {
        if (request.getRemoteAddr().equals("0:0:0:0:0:0:0:1")) {
            client.setClientIp("127.0.0.1");
        } else {
            client.setClientIp(request.getRemoteAddr());
        }
        client.setHttpMethod(request.getMethod());
        metaData.setClient(client);
        metaData.setStatus(status);
        result.setMetaData(metaData);
        ResultObject savedResult = resultObjectRepository.save(result);
        result.getMetaData().setRequestId(savedResult.getTransactionId());
        return result;
    }

}
