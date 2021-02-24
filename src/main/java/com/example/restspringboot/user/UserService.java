package com.example.restspringboot.user;

import com.example.restspringboot.auth.token.ConfirmationToken;
import com.example.restspringboot.auth.token.ConfirmationTokenService;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

  @Autowired private UserRepository userRepository;
  @Autowired private PasswordEncoder passwordEncoder;
  @Autowired private ConfirmationTokenService confirmationTokenService;

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

    // Send confirmation token;
    String token = UUID.randomUUID().toString();
    ConfirmationToken confirmationToken =
        new ConfirmationToken(
            token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), user);
    confirmationTokenService.saveConfirmationToken(confirmationToken);

    // TODO: Send email
    return token;
  }

  public int enabledUser(String email) {
    return userRepository.enableUser(email);
  }
}
