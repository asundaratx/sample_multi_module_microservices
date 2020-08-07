package org.example.microsvc.mobileapp.model.response;

import lombok.Data;

@Data
public class UserRest {
    private String firstName;
    private String lastName;
    private String email;
    private String userId;
}
