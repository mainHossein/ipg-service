package com.example.ipgserviceproject.model.dtos;

import com.example.ipgserviceproject.model.enums.AccountStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountStatusModifier {
    private long cardId;
    private String accountStatus;
}
