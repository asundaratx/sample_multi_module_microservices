package org.example.microsvc.mobileapp.ui.utils;

import feign.FeignException;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.example.microsvc.mobileapp.model.response.AccountRest;
import org.example.microsvc.mobileapp.ui.service.AccountServiceClient;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FeignAccountClientFallbackFactory implements FallbackFactory<AccountServiceClient> {
    @Override
    public AccountServiceClient create(Throwable throwable) {
        return new FeignAccountClientFallback(throwable);
    }
}

@Slf4j
class FeignAccountClientFallback implements AccountServiceClient{
    private Throwable cause;
    public FeignAccountClientFallback(Throwable throwable){
        this.cause = throwable;
    }
    @Override
    public List<AccountRest> getAccount(String userId) {
        if(cause instanceof FeignException){
            log.error(" Error in feign client: " + cause.getLocalizedMessage());
        } else {
            log.error(" Error: " + cause.getLocalizedMessage());
        }
        return new ArrayList<>();
    }
}
