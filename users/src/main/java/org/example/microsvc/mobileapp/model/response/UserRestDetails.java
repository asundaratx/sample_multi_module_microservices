package org.example.microsvc.mobileapp.model.response;

import lombok.Data;

import java.util.List;

@Data
public class UserRestDetails {
    private String firstName;
    private String lastName;
    private String email;
    private String userId;
    private List<String> accountId;
    private Double accountBalance;
}
