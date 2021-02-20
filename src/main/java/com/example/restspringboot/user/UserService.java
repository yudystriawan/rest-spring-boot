package com.example.restspringboot.user;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private userCredentialsRepository userCredentialsRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public User register(UserRequestBody user) {
        User newUser = new User(user.getName(), user.getEmail());
        User savedUser = userRepository.save(newUser);

        Logger.getLogger(UserService.class.getSimpleName()).info(savedUser.toString());

        UserCredentials userCredentials = new UserCredentials(user.getPassword(), savedUser);
        userCredentialsRepository.save(userCredentials);

        return savedUser;
    }

}
