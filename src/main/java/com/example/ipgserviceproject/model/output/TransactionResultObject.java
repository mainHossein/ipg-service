package com.example.ipgserviceproject.model.output;

import com.example.ipgserviceproject.model.dtos.TransactionDtoGet;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TransactionResultObject {
    @Embedded
    private TransactionDtoGet transaction;
    @Embedded
    private MetaData metaData;
}
