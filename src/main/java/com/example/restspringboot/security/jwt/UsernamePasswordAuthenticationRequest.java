package com.example.restspringboot.security.jwt;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UsernamePasswordAuthenticationRequest {

  private String username;
  private String password;
}
