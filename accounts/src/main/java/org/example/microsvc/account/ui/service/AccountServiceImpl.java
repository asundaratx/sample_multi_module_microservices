package org.example.microsvc.account.ui.service;

import lombok.extern.slf4j.Slf4j;
import org.example.microsvc.account.model.data.AccountModel;
import org.example.microsvc.account.model.repository.AccountRepository;
import org.example.microsvc.account.model.request.AccountRestRequest;
import org.example.microsvc.account.model.request.AccountUpdateRequest;
import org.example.microsvc.account.model.response.AccountRest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class AccountServiceImpl implements AccountServiceInterface {
    private AccountRepository accountRepository;
    private ModelMapper modelMapper;
    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository,
                              ModelMapper modelMapper) {
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
    }
/*
    private AccountRest createSampleAccount(String userId){
        AccountRest account = new AccountRest();
        account.setUserId(userId);
        account.setAccountId("1");
        account.setBalance(100d);
        return account;
    }

    @Override
    public AccountRest getAccount(String userId) {
        return createSampleAccount(userId);
    }
*/
    @Override
    public List<AccountRest> getAllAccounts() {
        List<AccountRest> accounts = new ArrayList<>();
        accountRepository.findAll().forEach(accountModel -> {
            accounts.add(modelMapper.map(accountModel, AccountRest.class));
        });
        log.info("Accounts are: "+ accounts.toString());
        return accounts;
    }

    @Override
    public AccountRest getAccountByAccountId(String accountId) {
        log.info("getting accounts for account: " + accountId);
        AccountModel accountModel = accountRepository.findByAccountId(accountId);
        return modelMapper.map(accountModel, AccountRest.class);
    }

    @Override
    public List<AccountRest> getAccountsByUserId(String userId) {
        log.info("getting accounts for user: " + userId);
        List<AccountRest> accounts = new ArrayList<>();
        List<AccountModel> accountModels = accountRepository.findByUserId(userId);
        accountModels.forEach(accountModel ->
            accounts.add(modelMapper.map(accountModel,AccountRest.class)));
        return accounts;
    }

    @Override
    public AccountRest createAccount(String userId, AccountRestRequest accountRestRequest) {
        log.info("creating new account for user: " + userId);
        String accountId = UUID.randomUUID().toString();
        AccountModel newaccount = modelMapper.map(accountRestRequest, AccountModel.class);
        newaccount.setAccountId(accountId);
        newaccount.setUserId(userId);
        accountRepository.save(newaccount);
        return getAccountByAccountId(accountId);
    }

    @Override
    public AccountRest updateAccount(String accountId, AccountUpdateRequest accountUpdateRequest) {
        AccountModel accountModel = accountRepository.findByAccountId(accountId);
        if (accountUpdateRequest.getIsCredit()) {
            accountModel.setBalance(accountModel.getBalance()+accountUpdateRequest.getTransactionAmount());
        } else {
            if(accountModel.getBalance()>=accountUpdateRequest.getTransactionAmount()){
                accountModel.setBalance(
                        accountModel.getBalance() - accountUpdateRequest.getTransactionAmount());
            } else {
                log.info("Insufficient funds for account: " + accountId);
            }
        }
        accountRepository.updateAccountByAccountId(accountModel.getBalance(),accountModel.getAccountId());
        return getAccountByAccountId(accountModel.getAccountId());
    }

    @Override
    public void deleteAccountByAccountId(String accountId) {
        log.info("Deleting account: "+accountId);
        accountRepository.deleteByAccountId(accountId);
    }

    @Override
    public void deleteAccountsByUserId(String userId) {
        log.info("Deleting accounts for user: "+userId);
        List<AccountModel> accounts = accountRepository.findByUserId(userId);
        accounts.forEach(accountModel -> deleteAccountByAccountId(accountModel.getAccountId()));
        return;
    }
}
