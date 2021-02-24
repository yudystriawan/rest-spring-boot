package com.example.restspringboot.auth;

import com.example.restspringboot.security.ApplicationUserRole;
import com.example.restspringboot.user.User;
import com.example.restspringboot.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  @Autowired private UserService userService;

  public String register(RegistrationRequest request) {
    return userService.signUp(
        new User(
            request.getName(),
            request.getUsername(),
            request.getEmail(),
            request.getPassword(),
            ApplicationUserRole.CUSTOMER));
  }
}
