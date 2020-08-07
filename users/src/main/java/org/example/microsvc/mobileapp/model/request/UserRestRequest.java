package org.example.microsvc.mobileapp.model.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Data
public class UserRestRequest {

    @NotNull(message = "Firstname cannot be empty")
    @Size(min = 2, max = 30)
    private String firstName;

    @NotNull(message = "Lastname cannot be empty")
    @Size(min = 2, max = 30)
    private String lastName;

    @Email
    private String email;
    private String password;
}
