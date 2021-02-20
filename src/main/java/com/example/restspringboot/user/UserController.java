package com.example.restspringboot.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> index() {
        return ResponseEntity.ok().body(userService.findAll());
    }

    @PostMapping
    public ResponseEntity<?> store(@RequestBody UserRequestBody user) {
        if (userService.emailExists(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email alreadt exist");
        }

        User savedUser = userService.register(user);

        return ResponseEntity.ok().body(savedUser);
    }

}
