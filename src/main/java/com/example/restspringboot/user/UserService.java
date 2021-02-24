package com.example.restspringboot.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

  @Autowired private UserRepository userRepository;
  @Autowired private PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return userRepository
        .findByEmail(email)
        .orElseThrow(
            () -> new UsernameNotFoundException(String.format("Email %s not found", email)));
  }

  public String signUp(User user) {
    boolean emailExist = userRepository.findByEmail(user.getEmail()).isPresent();
    boolean usernameExist = userRepository.findByUsername(user.getUsername()).isPresent();

    if (emailExist) throw new IllegalStateException("Email already taken.");

    if (usernameExist) throw new IllegalStateException("Username already taken.");

    String encodedPassword = passwordEncoder.encode(user.getPassword());
    user.setPassword(encodedPassword);

    userRepository.save(user);

    // TODO: Send confirmation token;

    return user.toString();
  }
}
