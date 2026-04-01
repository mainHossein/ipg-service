package com.example.ipgserviceproject.model.output;

import com.example.ipgserviceproject.model.dtos.AccountDtoGet;
import com.example.ipgserviceproject.model.entity.Account;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountResultObject {
    @Embedded
    private AccountDtoGet account;
    @Embedded
    private MetaData metaData;
}
