package com.example.restspringboot.resto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/restos")
public class RestoController {

    @GetMapping
    public ResponseEntity<?> index() {
        return ResponseEntity.ok().body("list resto");
    }
}
