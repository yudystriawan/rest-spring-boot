package com.example.restspringboot.jwt;

import io.jsonwebtoken.Jwts;
import java.time.LocalDate;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JwtService {
  private final JwtConfig jwtConfig;
  private final SecretKey secretKey;

  public JWT generateToken(Authentication authentication) {
    String token =
        Jwts.builder()
            .setSubject(authentication.getName())
            .claim("authorities", authentication.getAuthorities())
            .setIssuedAt(new Date())
            .setExpiration(
                java.sql.Date.valueOf(
                    LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays())))
            .signWith(secretKey)
            .compact();

    return new JWT(jwtConfig.getTokenPrefix(), token);
  }
}
