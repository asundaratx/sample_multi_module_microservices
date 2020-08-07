package org.example.microsvc.mobileapp.ui.service;

import org.example.microsvc.mobileapp.model.response.UserRestDetails;

public interface UserDetailsService {
    public UserRestDetails getUserDetails(String uid);

}
