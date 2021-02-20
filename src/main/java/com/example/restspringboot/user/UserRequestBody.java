package com.example.restspringboot.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserRequestBody {
    private String name;
    private String email;
    private String password;
}
