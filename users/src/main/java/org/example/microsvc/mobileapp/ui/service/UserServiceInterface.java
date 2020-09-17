package org.example.microsvc.mobileapp.ui.service;

import org.example.microsvc.mobileapp.model.request.UserRestRequest;
import org.example.microsvc.mobileapp.model.request.UserUpdateRequest;
import org.example.microsvc.mobileapp.model.response.UserRest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserServiceInterface extends UserDetailsService {
    public UserRest addUser(UserRestRequest user);
    public UserRest getUser(String uid);
    public List<UserRest> getAllUsers();
    public UserRest updateUser(String userId, UserUpdateRequest userRestRequest);
    public void deleteUser(String userId);
    public UserRest getUserByEmail(String email);
    public void validateUserByUserId(String userId);
}
