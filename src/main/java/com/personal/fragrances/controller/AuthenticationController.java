package com.personal.fragrances.controller;

import com.personal.fragrances.infra.auth.AuthenticationRequest;
import com.personal.fragrances.infra.auth.AuthenticationResponse;
import com.personal.fragrances.infra.auth.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AuthenticationController {

    private final UserDetailsService userDetailsService;
    private final UserDetailsManager userDetailsManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationController(UserDetailsService userDetailsService, UserDetailsManager userDetailsManager, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.userDetailsManager = userDetailsManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        if (userDetails != null && passwordEncoder.matches(authenticationRequest.getPassword(), userDetails.getPassword())) {
            String token = jwtUtil.generateToken(authenticationRequest.getUsername());
            return ResponseEntity.ok(new AuthenticationResponse(token));
        }
        return ResponseEntity.badRequest().body(new AuthenticationResponse("Invalid credentials"));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthenticationRequest authenticationRequest) {
        UserDetails user = User.builder()
                .username(authenticationRequest.getUsername())
                .password(passwordEncoder.encode(authenticationRequest.getPassword()))
                .authorities("USER")
                .build();

        userDetailsManager.createUser(user);

        if (userDetailsService.loadUserByUsername(authenticationRequest.getUsername()) != null) {
            return ResponseEntity.ok("User registered successfully");
        }

        return ResponseEntity.internalServerError().body("Registration error");
    }

}
