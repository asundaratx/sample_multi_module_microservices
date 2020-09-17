package org.example.microsvc.mobileapp.ui.utils;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()){
            case 404 :
                if(methodKey.contains("getAccount")) {
                    return new ResponseStatusException(HttpStatus.valueOf(response.status()), "unable to get account");
                }
                break;
            default: return new ApplicationException("Error in feign usage: " + response.reason() );
        }
        return null;
    }
}
