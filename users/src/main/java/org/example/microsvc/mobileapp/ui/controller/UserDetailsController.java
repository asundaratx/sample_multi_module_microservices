package org.example.microsvc.mobileapp.ui.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.microsvc.mobileapp.model.response.UserRestDetails;
import org.example.microsvc.mobileapp.ui.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserDetailsController {
    private UserDetailsService userService;

    @Autowired
    public UserDetailsController(UserDetailsService userService){
        this.userService = userService;
    }

    @GetMapping(path="/users/details/{userId}",
            produces = {
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE
            })
    public UserRestDetails getUserDetailsById(@PathVariable String userId){
        return userService.getUserDetails(userId);
    }
}
