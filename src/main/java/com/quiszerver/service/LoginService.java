package com.quiszerver.service;


import com.quiszerver.model.User;
import com.quiszerver.repository.UserRepository;
import com.quiszerver.requests.LoginRequest;
import com.quiszerver.response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    public LoginResponse authenticateUser(LoginRequest loginRequest) {
        User user = userRepository.findByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());
        boolean isAuthenticated = user != null;

        return new LoginResponse(user, isAuthenticated);
    }
}
