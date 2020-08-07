package org.example.microsvc.account.ui.service;

import org.example.microsvc.account.model.response.AccountRest;

import java.util.ArrayList;
import java.util.List;

public class AccountServiceImpl implements AccountServiceInterface {

    private AccountRest getSampleAccount(String userId){
        AccountRest account = new AccountRest();
        account.setUserId(userId);
        account.setAccountId("1");
        account.setAccountBalance(100d);
        return account;
    }

    @Override
    public List<AccountRest> getAllAccounts() {
        List<AccountRest> accounts = new ArrayList<>();
        AccountRest account = getSampleAccount("a1b2-c3d4-e5f6-g7h8");
        AccountRest account2 = getSampleAccount("b1b2-d3d4-f5f6-h7h8");
        accounts.add(account);
        accounts.add(account2);
        return accounts;
    }

    @Override
    public AccountRest getAccount(String userId) {
        return getSampleAccount(userId);
    }
}
