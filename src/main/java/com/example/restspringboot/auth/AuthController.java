package com.example.restspringboot.auth;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/auth")
@AllArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/register")
  public String register(@Valid @RequestBody RegistrationRequest request) {
    return authService.register(request);
  }

  @GetMapping("/confirm")
  public String confirm(@RequestParam("token") String token) {
    return authService.confirmToken(token);
  }

  // TODO: ERROR HANDLING
}
