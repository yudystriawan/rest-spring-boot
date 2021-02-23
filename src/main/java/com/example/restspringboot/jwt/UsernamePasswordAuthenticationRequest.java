package com.example.restspringboot.jwt;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UsernamePasswordAuthenticationRequest {

    private String username;
    private String password;
}
