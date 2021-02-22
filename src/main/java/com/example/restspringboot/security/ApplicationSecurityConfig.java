package com.example.restspringboot.security;

import static com.example.restspringboot.security.ApplicationUserRole.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().anyRequest().authenticated().and().httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails johnDoe = User.builder().username("johndoe").password(passwordEncoder.encode("password"))
                .authorities(CUSTOMER.getGrantedAuthorities()).build();

        UserDetails anna = User.builder().username("anna").password(passwordEncoder.encode("password"))
                .authorities(OWNER.getGrantedAuthorities()).build();

        UserDetails alex = User.builder().username("alex").password(passwordEncoder.encode("password"))
                .authorities(STAFF.getGrantedAuthorities()).build();

        return new InMemoryUserDetailsManager(johnDoe, anna, alex);
    }

}
