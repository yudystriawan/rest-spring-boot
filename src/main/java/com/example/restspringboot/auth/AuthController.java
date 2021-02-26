package com.example.restspringboot.auth;

import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/auth")
@AllArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/register")
  public String register(@Valid @RequestBody RegistrationRequest request) {
    return authService.register(request);
  }

  @PostMapping("/login")
  public String login(@RequestBody LoginRequest request) {
    return authService.login(request);
  }

  @GetMapping("/confirm")
  public String confirmAccount(@RequestParam("token") String token) {
    return authService.confirmAccount(token);
  }

  // TODO: ERROR HANDLING
}
