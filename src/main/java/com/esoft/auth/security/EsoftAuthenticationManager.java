package com.esoft.auth.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;

import java.util.ArrayList;

@Component
public class EsoftAuthenticationManager implements AuthenticationManager {
    private final AuthenticationProvider authenticationProvider;

    public EsoftAuthenticationManager(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

//        if ("validUsername".equals(username) && "validPassword".equals(password)) {
            return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
//        } else {
//            throw new BadCredentialsException("Invalid credentials");
//        }
    }
}
