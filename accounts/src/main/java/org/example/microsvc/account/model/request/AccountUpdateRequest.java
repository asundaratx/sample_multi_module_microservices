package org.example.microsvc.account.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AccountUpdateRequest {
    @NotNull
    Double transactionAmount;
    @NotNull
    Boolean isCredit;
}
