package com.example.restspringboot.security.jwt;

import com.google.common.base.Strings;
import io.jsonwebtoken.*;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import javax.servlet.*;
import javax.servlet.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtTokenVerifier extends OncePerRequestFilter {

  private final JwtConfig jwtConfig;
  private final SecretKey secretKey;

  @Override
  @SuppressWarnings("unchecked")
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String authorizationHeader = request.getHeader(jwtConfig.getAuthorizationHeader());

    if (Strings.isNullOrEmpty(authorizationHeader)
        || !authorizationHeader.startsWith(jwtConfig.getTokenPrefix())) {
      filterChain.doFilter(request, response);
      return;
    }

    String token = authorizationHeader.replace(jwtConfig.getTokenPrefix(), "");

    try {
      Jws<Claims> parseClaimsJws =
          Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);

      Claims body = parseClaimsJws.getBody();

      String username = body.getSubject();

      var authorities = (List<Map<String, String>>) body.get("authorities");

      Set<SimpleGrantedAuthority> simpleGrantedAuthorities =
          authorities.stream()
              .map(map -> new SimpleGrantedAuthority(map.get("role")))
              .collect(Collectors.toSet());

      Authentication authentication =
          new UsernamePasswordAuthenticationToken(username, null, simpleGrantedAuthorities);

      SecurityContextHolder.getContext().setAuthentication(authentication);

    } catch (JwtException e) {
      throw new IllegalStateException(String.format("Token %s cannot be trusted.", token));
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Something went wrong.");
    }

    filterChain.doFilter(request, response);
  }

  public JwtTokenVerifier(JwtConfig jwtConfig, SecretKey secretKey) {
    this.jwtConfig = jwtConfig;
    this.secretKey = secretKey;
  }
}
