package org.example.microsvc.mobileapp.model.data;

import lombok.Data;

@Data
public class LoginRequestModel {
    private String username;
    private String password;
}
