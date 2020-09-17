package org.example.microsvc.mobileapp.ui.service;

import lombok.extern.slf4j.Slf4j;
import org.example.microsvc.mobileapp.model.repository.UserRepository;
import org.example.microsvc.mobileapp.model.response.AccountRest;
import org.example.microsvc.mobileapp.model.response.UserRestDetails;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService{
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    RestTemplate restTemplate;
    AccountServiceClient accountServiceClient;
    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository,
                                  ModelMapper modelMapper,
                                  RestTemplate restTemplate,
                                  AccountServiceClient accountServiceClient){
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.restTemplate = restTemplate;
        this.accountServiceClient = accountServiceClient;

    }
    public UserRestDetails getUserDetails(String uid){
        return  getUserDetailsFeign(uid);
    }

    private  UserRestDetails getUserDetailsFeign(String uid){
        UserRestDetails userDetails = modelMapper.map(userRepository.findByUserId(uid), UserRestDetails.class);
        log.info("Retrieved details: " + userDetails);
        List<AccountRest> accounts = accountServiceClient.getAccount(uid);
        List<String> accountIds = new ArrayList<>();
        Double balance = accounts.stream().mapToDouble(account -> account.getBalance()).sum();
        accounts.forEach(account -> {
            accountIds.add(account.getAccountId());
                }
        );
        userDetails.setAccountId(accountIds);
        userDetails.setAccountBalance(balance);
        return  userDetails;
    }

    /*Uses blocking rest template to get account details*/
    private UserRestDetails getUserDetailsBlocking(String uid){
        UserRestDetails userDetails = modelMapper.map(userRepository.findByUserId(uid), UserRestDetails.class);
        String accountUrl = String.format("http://accounts-ws/%s/accounts",uid);
        ResponseEntity<List<AccountRest>> userAccounts =
                restTemplate.exchange(accountUrl, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<AccountRest>>(){});
        List<AccountRest> accounts = userAccounts.getBody();
        if(accounts != null) {
            List<String> accountIds = new ArrayList<>();
            Double balance = accounts.stream().mapToDouble(account -> account.getBalance()).sum();
            accounts.forEach(account -> {
                        accountIds.add(account.getAccountId());
                    }
            );
            userDetails.setAccountId(accountIds);
            userDetails.setAccountBalance(balance);
        }
        return  userDetails;
    }
}
