package org.example.microsvc.mobileapp.ui.service;

import lombok.extern.slf4j.Slf4j;
import org.example.microsvc.mobileapp.model.data.UserModel;
import org.example.microsvc.mobileapp.model.repository.UserRepository;
import org.example.microsvc.mobileapp.model.request.UserRestRequest;
import org.example.microsvc.mobileapp.model.request.UserUpdateRequest;
import org.example.microsvc.mobileapp.model.response.UserRest;
import org.example.microsvc.mobileapp.ui.utils.MessageSender;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserServiceInterface{
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private MessageSender messageSender;
    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper modelMapper,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           MessageSender messageSender){
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.messageSender = messageSender;
    }

    private UserRest convertToUserRest(UserModel userModel) {
        return modelMapper.map(userModel, UserRest.class);
    }

    public UserRest addUser(UserRestRequest user){
        String uid = UUID.randomUUID().toString();
        UserModel newuser = modelMapper.map(user, UserModel.class);
        newuser.setUserId(uid);
        newuser.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(newuser);
        return getUser(uid);
    }

    public UserRest getUser(String uid){
        return modelMapper.map(userRepository.findByUserId(uid), UserRest.class);
    }

    public UserRest getUserByEmail(String email){
        return modelMapper.map(userRepository.findByEmail(email), UserRest.class);
    }
    public List<UserRest> getAllUsers(){
        List<UserModel> userModelList = new ArrayList<>();
        Iterable<UserModel> users = userRepository.findAll();
        users.forEach(userModelList::add);
        return userModelList.stream().map(this::convertToUserRest).collect(Collectors.toList());
    }

    public UserRest updateUser(String userId, UserUpdateRequest userRestRequest){
        userRepository.updateUserByUserId(userRestRequest.getFirstName(), userRestRequest. getLastName(),
                userRestRequest.getEmail(), userId);
        UserRest storedUser = convertToUserRest(userRepository.findByUserId(userId));
        return storedUser;
    }

    public void deleteUser(String userId){
        userRepository.deleteByUserId(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String useremail) throws UsernameNotFoundException {
        UserModel user = userRepository.findByEmail(useremail);
        return new User(user.getEmail(), user.getEncryptedPassword(),
                true, true,
                true, true,
                new ArrayList<>());
    }

    @Override
    public void validateUserByUserId(String userId) {
        log.info("Validating user with id:" + userId);
        UserModel userModel = userRepository.findByUserId(userId);
        if (userModel !=null && userModel.getEmail()!=null) {
            messageSender.send("isvalid",userId);
        } else {
            messageSender.send("isinvalid",userId);
        }
    }
}
