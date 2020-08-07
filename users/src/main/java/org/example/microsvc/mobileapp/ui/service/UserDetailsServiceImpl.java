package org.example.microsvc.mobileapp.ui.service;

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

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    RestTemplate restTemplate;
    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository,
                                  ModelMapper modelMapper,
                                  RestTemplate restTemplate){
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.restTemplate = restTemplate;

    }
    public UserRestDetails getUserDetails(String uid){
        UserRestDetails userDetails = modelMapper.map(userRepository.findByUserId(uid), UserRestDetails.class);
        String accountUrl = String.format("http://accounts-ws/accounts/%s",uid);
        ResponseEntity<AccountRest> userAccount = restTemplate.exchange(accountUrl, HttpMethod.GET, null,
                new ParameterizedTypeReference<AccountRest>(){});
        AccountRest account = userAccount.getBody();
        userDetails.setAccountId(account.getAccountId());
        userDetails.setAccountBalance(account.getAccountBalance());
        return  userDetails;
    }
}
