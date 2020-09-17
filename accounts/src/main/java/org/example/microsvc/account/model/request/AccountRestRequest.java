package org.example.microsvc.account.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AccountRestRequest {
    @NotNull(message = "balance cannot be empty")
    Double balance;
}
