package org.example.microsvc.account.ui.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.microsvc.account.model.request.AccountRestRequest;
import org.example.microsvc.account.model.request.AccountUpdateRequest;
import org.example.microsvc.account.model.response.AccountRest;
import org.example.microsvc.account.ui.service.AccountServiceInterface;
import org.example.microsvc.account.ui.service.UserMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class AccountsController {
    private AccountServiceInterface accountServiceInterface;
    private UserMessageSender userMessageSender;

    @Autowired
    public AccountsController(AccountServiceInterface accountServiceInterface,
                              UserMessageSender userMessageSender){
        this.accountServiceInterface = accountServiceInterface;
        this.userMessageSender = userMessageSender;
    }

    @GetMapping(path = "/accounts",
            produces = {
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<List<AccountRest>> getAccounts(@RequestParam(value="page", defaultValue = "1") int page,
                                                  @RequestParam(value = "limit", defaultValue = "10") int limit,
                                                  @RequestParam(value = "sort", defaultValue = "desc",required = false) String sort){
        log.info("Getting accounts in accounts microservice");
        return new ResponseEntity<List<AccountRest>>(accountServiceInterface.getAllAccounts(), HttpStatus.OK);
    }

    @GetMapping(path = "/{userId}/accounts",
            produces = {
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<List<AccountRest>> getAccountByUserId(@PathVariable String userId){
        return new ResponseEntity<List<AccountRest>>(
                accountServiceInterface.getAccountsByUserId(userId), HttpStatus.OK);
    }

    @GetMapping(path = "/{userId}/accounts/{accountId}",
            produces = {
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE
            })
    public ResponseEntity<AccountRest> getAccountByAccountId(@PathVariable String userId ,
                                                             @PathVariable String accountId){
        return new ResponseEntity<AccountRest>(
                accountServiceInterface.getAccountByAccountId(accountId), HttpStatus.OK);
    }

    @PostMapping (path = "/{userId}/accounts",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<AccountRest> createAccount(@PathVariable String userId ,
                                                     @Valid @RequestBody AccountRestRequest accountRestRequest,
                                               BindingResult bindingResult ){
        if (bindingResult.hasErrors())
            throw new RuntimeException("Invalid input parameters");
        userMessageSender.send("userId",userId);
        AccountRest account = accountServiceInterface.createAccount(userId,accountRestRequest);
        return new ResponseEntity<AccountRest>(account, HttpStatus.OK);
    }

    @PutMapping(path = "/{userId}/accounts/{accountId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<AccountRest> updateAccount(@Valid @PathVariable String accountId,
                                               @RequestBody AccountUpdateRequest accountUpdateRequest,
                                               BindingResult bindingResult){
        if (bindingResult.hasErrors())
            throw new RuntimeException("Invalid input parameters");
        return new ResponseEntity<AccountRest>(accountServiceInterface.updateAccount(accountId, accountUpdateRequest), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{userId}/accounts/{accountId}")
    public void deleteAccount(@PathVariable String accountId){
        accountServiceInterface.deleteAccountByAccountId(accountId);
    }

}
