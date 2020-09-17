package org.example.microsvc.mobileapp.model.response;

import lombok.Data;

@Data
public class AccountRest {
    private String userId;
    private String accountId;
    private Double balance;
}
