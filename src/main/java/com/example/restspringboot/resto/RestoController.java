package com.example.restspringboot.resto;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/restos")
public class RestoController {

  @GetMapping
  public ResponseEntity<?> index() {
    return ResponseEntity.ok().body("list resto");
  }

  @PostMapping
  @PreAuthorize("hasAuthority('owner:write')")
  public ResponseEntity<?> store() {
    return ResponseEntity.ok().body("create resto");
  }
}
