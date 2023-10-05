package com.esoft.auth.security;

import com.esoft.auth.security.jwt.JwtAuthenticationFilter;
import com.esoft.auth.security.jwt.JwtAuthorizationFilter;
import com.esoft.auth.service.TokenBlacklistService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final JwtTokenProvider jwtTokenProvider;
  private final UserDetailsService userDetailsService;
  private final TokenBlacklistService tokenBlacklistService;
  public SecurityConfig(JwtTokenProvider jwtTokenProvider,
                        UserDetailsService userDetailsService,
                        TokenBlacklistService tokenBlacklistService) {
    this.jwtTokenProvider = jwtTokenProvider;
    this.userDetailsService = userDetailsService;
    this.tokenBlacklistService = tokenBlacklistService;
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    return new EsoftAuthenticationProvider(userDetailsService, passwordEncoder());
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) {
    auth.authenticationProvider(authenticationProvider());
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
            .authorizeRequests()
            .antMatchers(
                    "/",
                    "/static/index.html",
                    "/api/auth/register",
                    "/api/auth/refresh",
                    "/v3/api-docs/**",
                    "/swagger-ui/**",
                    "/swagger-resources/**").permitAll() // Allow registration without authentication
            .anyRequest().authenticated() // Require authentication for other endpoints
            .and()
            .logout()
            .and()
            .addFilter(new JwtAuthenticationFilter(authenticationManager(), userDetailsService(), jwtTokenProvider))
            .addFilter(new JwtAuthorizationFilter(authenticationManager(), jwtTokenProvider, tokenBlacklistService))
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }
}