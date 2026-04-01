package com.example.ipgserviceproject.repository;

import com.example.ipgserviceproject.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByCardId(long cardId);

    @Query("""
        SELECT a.balance FROM Account a WHERE a.cardId = :cardId
""")
    long getBalance(long cardId);

    @Query("""
        SELECT a.status FROM Account a WHERE a.cardId = :cardId
""")
    String accountStatus(long cardId);

    @Query("""
        SELECT a.ownerNationalId FROM Account a WHERE a.cardId = :cardId
""")
    long getAccountOwnerNationalId(long cardId);

}
