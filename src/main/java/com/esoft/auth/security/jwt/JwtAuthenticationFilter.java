package com.esoft.auth.security.jwt;

import com.esoft.auth.security.JwtTokenProvider;
import com.esoft.auth.security.model.LoginReq;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    protected final UserDetailsService userDetailsService;
    protected final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest req,
            HttpServletResponse res) throws AuthenticationException {
        try {
            LoginReq loginReq = new ObjectMapper().readValue(req.getInputStream(), LoginReq.class);
            if (!loginReq.getUsername().isEmpty() && !loginReq.getPassword().isEmpty()) {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword())
                );
                return authentication;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain,
            Authentication authentication) throws IOException {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);

        String refreshToken = jwtTokenProvider.generateRefreshToken(authentication);

        response.setContentType("application/json");
        response.getWriter().write("{\"Authorization\": \"" + token + "\", \"Refresh-Token\": \"" + refreshToken + "\"}");
        response.addHeader("Authorization", token);
        response.addHeader("Refresh-Token", refreshToken);
        response.setStatus(HttpStatus.OK.value());
    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
            throws IOException {
        SecurityContextHolder.clearContext();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.addHeader("Content-Type", "application/json;charset=UTF-8");
        response.getWriter().println("{\"ErrorMessage\": \"Login failed\"}");
//        log.info(InternationalizationMessage.getString("error.login_fail"));
    }

}
