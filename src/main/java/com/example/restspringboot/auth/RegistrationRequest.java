package com.example.restspringboot.auth;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Data
public class RegistrationRequest {
  @NotBlank(message = "Name cannot be empty")
  private final String name;

  @Email(message = "Email should be valid")
  private final String email;

  @NotBlank(message = "Password cannot be empty")
  private final String password;

  @NotBlank(message = "Username cannot be empty")
  private final String username;
}