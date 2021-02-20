package com.example.restspringboot.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Autowired
    private UserRepository userRepository;

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            User john = new User("John Doe", "johndoe@example.com");
            User alex = new User("Alex Myth", "alexmyth@example.com");

            userRepository.saveAll(List.of(john, alex));
        };
    }
}
