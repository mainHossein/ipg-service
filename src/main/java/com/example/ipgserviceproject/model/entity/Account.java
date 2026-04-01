package com.example.ipgserviceproject.model.entity;

import com.example.ipgserviceproject.model.enums.AccountStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Account {
    @Id
    @SequenceGenerator(
            name = "cardIdGenerator",
            sequenceName = "card_id_seq",
            initialValue = 100000000,
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "cardIdGenerator"
    )
    private long cardId;
    @Column(name = "nationalId")
    private long ownerNationalId;
    private long balance;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Transaction> transactions;
    @CreationTimestamp
    private Timestamp createdAt;
}
