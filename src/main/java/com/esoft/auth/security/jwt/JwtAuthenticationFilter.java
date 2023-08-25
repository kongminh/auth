package com.esoft.auth.security.jwt;

import com.esoft.auth.security.JwtTokenProvider;
import com.esoft.auth.security.model.LoginReq;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    protected UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest req,
            HttpServletResponse res) throws AuthenticationException {
        try {
            LoginReq loginReq = new ObjectMapper().readValue(req.getInputStream(), LoginReq.class);
            if (!loginReq.getUsername().isEmpty() && !loginReq.getPassword().isEmpty()) {
//                Authentication userAuth =
//                        this.authenticate(
//                                new CargoLinkAuthentication(
//                                        loginReq.getLoginId(),
//                                        loginReq.getPassword(),
//                                        new ArrayList<>(),
//                                        loginReq.getUserType(),
//                                        loginReq.getDeviceToken(),
//                                        loginReq.getDeviceType(),
//                                        loginReq.getIsAdmin(),
//                                        loginReq.isRemember()),
//                                userDetailsService);
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword())
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
                return authentication;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
