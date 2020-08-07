package org.example.microsvc.account.ui.service;

import org.example.microsvc.account.model.response.AccountRest;

import java.util.List;

public interface AccountServiceInterface {
    public List<AccountRest> getAllAccounts();
    public AccountRest getAccount(String userId);
}
