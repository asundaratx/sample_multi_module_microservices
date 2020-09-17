package org.example.microsvc.account.ui.service;

import org.example.microsvc.account.model.request.AccountRestRequest;
import org.example.microsvc.account.model.request.AccountUpdateRequest;
import org.example.microsvc.account.model.response.AccountRest;

import java.util.List;

public interface AccountServiceInterface {
    public List<AccountRest> getAllAccounts();
    public AccountRest getAccountByAccountId(String accountId);
    public List<AccountRest> getAccountsByUserId(String userId);
    public AccountRest createAccount(String userId, AccountRestRequest accountRestRequest);
    public AccountRest updateAccount(String accountId,AccountUpdateRequest accountUpdateRequest);
    public void deleteAccountByAccountId(String accountId);
    public void deleteAccountsByUserId(String userId);
}
