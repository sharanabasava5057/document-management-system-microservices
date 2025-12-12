package com.example.metadata.service;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) {

        if ("admin".equals(username)) {
            return User.withUsername("admin")
                    .password("{noop}admin123")
                    .authorities("ROLE_ADMIN")
                    .build();
        }

        if ("user".equals(username)) {
            return User.withUsername("user")
                    .password("{noop}user123")
                    .authorities("ROLE_USER")
                    .build();
        }

        throw new UsernameNotFoundException("User not found");
    }
}
