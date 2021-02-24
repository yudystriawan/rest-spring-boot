package com.example.restspringboot.user;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
public class UserController {

  @GetMapping
  public String index() {
    return "login";
  }

  // @PostMapping
  // public ResponseEntity<?> store(@RequestBody UserRequestBody user) {
  // // if (userService.emailExists(user.getEmail())) {
  // // return ResponseEntity.badRequest().body("Email alreadt exist");
  // // }

  // // User savedUser = userService.register(user);

  // // return ResponseEntity.ok().body(savedUser);
  // }

}
