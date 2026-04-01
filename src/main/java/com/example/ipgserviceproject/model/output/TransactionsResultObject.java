package com.example.ipgserviceproject.model.output;

import com.example.ipgserviceproject.model.dtos.TransactionDtoGet;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class TransactionsResultObject {
    private Set<TransactionDtoGet> transaction;
    @Embedded
    private MetaData metaData;
}
