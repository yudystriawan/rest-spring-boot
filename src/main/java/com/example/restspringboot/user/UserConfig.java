package com.example.restspringboot.user;

import static com.example.restspringboot.security.ApplicationUserRole.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserConfig {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            User john = new User("john", "john@example.com", passwordEncoder.encode("password"), CUSTOMER);
            User anna = new User("anna", "anna@example.com", passwordEncoder.encode("password"), OWNER);

            userRepository.saveAll(List.of(john, anna));
        };
    }
}
