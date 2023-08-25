package com.esoft.auth.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;

import java.util.ArrayList;

@Component
public class EsoftAuthenticationManager implements AuthenticationManager {

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        // Implement your own logic to authenticate the user
        // For example, check the provided username and password against your user repository
        // If valid, return an authenticated token; if invalid, throw AuthenticationException

        // Example logic (replace with your actual authentication logic)
        if ("validUsername".equals(username) && "validPassword".equals(password)) {
            return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
        } else {
            throw new BadCredentialsException("Invalid credentials");
        }
    }
}
