package com.example.restspringboot.security;

import com.example.restspringboot.security.jwt.JwtConfig;
import com.example.restspringboot.security.jwt.JwtTokenVerifier;
import com.example.restspringboot.security.jwt.JwtUsernamePasswordAuthenticationFilter;
import com.example.restspringboot.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.crypto.SecretKey;

import static com.example.restspringboot.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

  private final PasswordEncoder passwordEncoder;
  private final UserService userService;
  private final JwtConfig jwtConfig;
  private final SecretKey secretKey;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf()
        .disable()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilter(
            new JwtUsernamePasswordAuthenticationFilter(
                authenticationManager(), jwtConfig, secretKey))
        .addFilterAfter(
            new JwtTokenVerifier(jwtConfig, secretKey),
            JwtUsernamePasswordAuthenticationFilter.class)
        .authorizeRequests()
        .antMatchers("/api/auth/**")
        .permitAll()
        .anyRequest()
        .authenticated();
  }

  @Override
  @Bean
  protected UserDetailsService userDetailsService() {
    UserDetails johnDoe =
        User.builder()
            .username("john")
            .password(passwordEncoder.encode("password"))
            .authorities(CUSTOMER.getGrantedAuthorities())
            .build();

    UserDetails anna =
        User.builder()
            .username("anna")
            .password(passwordEncoder.encode("password"))
            .authorities(OWNER.getGrantedAuthorities())
            .build();

    UserDetails alex =
        User.builder()
            .username("alex")
            .password(passwordEncoder.encode("password"))
            .authorities(STAFF.getGrantedAuthorities())
            .build();

    return new InMemoryUserDetailsManager(johnDoe, anna, alex);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(daoAuthenticationProvider());
  }

  @Bean
  public DaoAuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(passwordEncoder);
    provider.setUserDetailsService(userService);
    return provider;
  }
}
