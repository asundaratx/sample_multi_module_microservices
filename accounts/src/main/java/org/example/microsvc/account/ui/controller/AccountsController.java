package org.example.microsvc.account.ui.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.microsvc.account.model.response.AccountRest;
import org.example.microsvc.account.ui.service.AccountServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("accounts")
@Slf4j
public class AccountsController {
    private AccountServiceInterface accountServiceInterface;

    @Autowired
    public AccountsController(AccountServiceInterface accountServiceInterface){
        this.accountServiceInterface = accountServiceInterface;
    }

    @GetMapping(produces = {
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<List<AccountRest>> getAccounts(@RequestParam(value="page", defaultValue = "1") int page,
                                                  @RequestParam(value = "limit", defaultValue = "10") int limit,
                                                  @RequestParam(value = "sort", defaultValue = "desc",required = false) String sort){
        log.info("Getting accounts in accounts microservice");
        return new ResponseEntity<List<AccountRest>>(accountServiceInterface.getAllAccounts(), HttpStatus.OK);
    }

    @GetMapping(path = "/{userId}",
            produces = {
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<AccountRest> getAccountByUserId(@PathVariable String userId){
        return new ResponseEntity<AccountRest>(accountServiceInterface.getAccount(userId), HttpStatus.OK);
    }
}
