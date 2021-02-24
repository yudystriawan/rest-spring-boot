package com.example.restspringboot.auth;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/auth")
public class AuthController {

  @Autowired private AuthService authService;

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
