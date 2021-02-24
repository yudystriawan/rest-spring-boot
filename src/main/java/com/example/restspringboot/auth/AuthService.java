package com.example.restspringboot.auth;

import com.example.restspringboot.auth.token.ConfirmationToken;
import com.example.restspringboot.auth.token.ConfirmationTokenService;
import com.example.restspringboot.security.ApplicationUserRole;
import com.example.restspringboot.user.User;
import com.example.restspringboot.user.UserService;
import java.time.LocalDateTime;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  @Autowired private UserService userService;
  @Autowired private ConfirmationTokenService confirmationTokenService;

  public String register(RegistrationRequest request) {
    return userService.signUp(
        new User(
            request.getName(),
            request.getUsername(),
            request.getEmail(),
            request.getPassword(),
            ApplicationUserRole.CUSTOMER));
  }

  @Transactional
  public String confirmToken(String token) {
    ConfirmationToken confirmationToken =
        confirmationTokenService
            .getToken(token)
            .orElseThrow(() -> new IllegalStateException("Token not found."));

    if (confirmationToken.getConfirmedAt() != null) {
      throw new IllegalStateException("email already confirmed");
    }

    LocalDateTime expiresAt = confirmationToken.getExpiresAt();

    if (expiresAt.isBefore(LocalDateTime.now())) {
      throw new IllegalStateException("Token expired");
    }

    confirmationTokenService.setConfirmedAt(token);
    userService.enabledUser(confirmationToken.getUser().getEmail());

    return "confirmed";
  }
}
