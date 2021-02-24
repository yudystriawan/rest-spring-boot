package com.example.restspringboot.auth;

import com.example.restspringboot.auth.token.ConfirmationToken;
import com.example.restspringboot.auth.token.ConfirmationTokenService;
import com.example.restspringboot.security.ApplicationUserRole;
import com.example.restspringboot.user.User;
import com.example.restspringboot.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AuthService {

  private final UserService userService;
  private final ConfirmationTokenService confirmationTokenService;

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
