package com.example.restspringboot.security;

import static com.example.restspringboot.security.ApplicationUserRole.*;

import javax.crypto.SecretKey;

import com.example.restspringboot.security.jwt.*;
import com.example.restspringboot.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Autowired
        private UserService userService;

        @Autowired
        private JwtConfig jwtConfig;

        @Autowired
        private SecretKey secretKey;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
                http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                                .addFilter(new JwtUsernamePasswordAuthenticationFilter(authenticationManager(),
                                                jwtConfig, secretKey))
                                .addFilterAfter(new JwtTokenVerifier(jwtConfig, secretKey),
                                                JwtUsernamePasswordAuthenticationFilter.class)
                                .authorizeRequests().anyRequest().authenticated();
        }

        @Override
        @Bean
        protected UserDetailsService userDetailsService() {
                UserDetails johnDoe = User.builder().username("john").password(passwordEncoder.encode("password"))
                                .authorities(CUSTOMER.getGrantedAuthorities()).build();

                UserDetails anna = User.builder().username("anna").password(passwordEncoder.encode("password"))
                                .authorities(OWNER.getGrantedAuthorities()).build();

                UserDetails alex = User.builder().username("alex").password(passwordEncoder.encode("password"))
                                .authorities(STAFF.getGrantedAuthorities()).build();

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
