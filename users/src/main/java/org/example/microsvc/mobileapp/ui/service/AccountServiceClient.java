package org.example.microsvc.mobileapp.ui.service;

import org.example.microsvc.mobileapp.model.response.AccountRest;
import org.example.microsvc.mobileapp.ui.utils.FeignAccountClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "accounts-ws", fallbackFactory = FeignAccountClientFallbackFactory.class)
public interface AccountServiceClient {
    @GetMapping("/{userId}/accounts")
    public List<AccountRest> getAccount(@PathVariable String userId);
}


