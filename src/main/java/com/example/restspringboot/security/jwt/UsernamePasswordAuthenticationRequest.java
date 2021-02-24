package com.example.restspringboot.security.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsernamePasswordAuthenticationRequest {

  private String username;
  private String password;
}
