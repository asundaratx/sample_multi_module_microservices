package org.example.microsvc.mobileapp.ui.controller;

import org.example.microsvc.mobileapp.model.request.UserRestRequest;
import org.example.microsvc.mobileapp.model.request.UserUpdateRequest;
import org.example.microsvc.mobileapp.model.response.UserRest;
import org.example.microsvc.mobileapp.model.response.UserRestDetails;
import org.example.microsvc.mobileapp.ui.service.UserServiceImpl;
import org.example.microsvc.mobileapp.ui.service.UserServiceInterface;
import org.example.microsvc.mobileapp.ui.utils.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("users")
@Slf4j
public class UserController {

    private UserServiceInterface userService;

    @Autowired
    public UserController(UserServiceInterface userService){
        this.userService = userService;
    }

    private List<UserRest> getSampleUsers(){
        List<UserRest> users = new ArrayList<>();
        UserRest user = new UserRest();
        user.setEmail("myemail@gmail.com");
        user.setFirstName("myFirstName");
        user.setLastName("myLastName");
        user.setUserId("1");
        users.add(user);
        return users;
    }
    @GetMapping(produces = {
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<List<UserRest>> getUser(@RequestParam(value="page", defaultValue = "1") int page,
                                                 @RequestParam(value = "limit", defaultValue = "10") int limit,
                                                 @RequestParam(value = "sort", defaultValue = "desc",required = false) String sort){
        log.info("Getting user in user microservice");
        return new ResponseEntity<List<UserRest>>(getSampleUsers(), HttpStatus.OK);
    }

    @GetMapping(path="/{userId}",
            produces = {
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE
            })
    public UserRest getUserById(@PathVariable String userId){
        return userService.getUser(userId);
    }

    @PostMapping (
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<UserRest> createUser(@Valid @RequestBody UserRestRequest userRestRequest,
                                               BindingResult bindingResult ){
        if (bindingResult.hasErrors())
            throw new ApplicationException("Invalid input parameters");
        UserRest user = userService.addUser(userRestRequest);
        return new ResponseEntity<UserRest>(user, HttpStatus.OK);
    }

    @PutMapping(path = "/{userId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserRest> updateUser(@Valid @PathVariable String userId,
                                               @RequestBody UserUpdateRequest userUpdateRequest,
                                               BindingResult bindingResult){
        if (bindingResult.hasErrors())
            throw new ApplicationException("Invalid input parameters");
        return new ResponseEntity<UserRest>(userService.updateUser(userId, userUpdateRequest), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{userId}")
    public void deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
    }
}
