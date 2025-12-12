package com.example.metadata.controller;

import com.example.metadata.dto.JwtRequest;
import com.example.metadata.util.JwtTokenUtil;
import com.example.metadata.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtRequest request) {

        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(401).body("Invalid Credentials");
        }

        UserDetails details = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtTokenUtil.generateToken(details.getUsername());

        return ResponseEntity.ok(
                java.util.Map.of("token", token, "username", details.getUsername())
        );
    }
}
