package com.esoft.auth.security;

import com.esoft.auth.security.jwt.JwtAuthenticationFilter;
import com.esoft.auth.security.jwt.JwtAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final JwtTokenProvider jwtTokenProvider;
  private final UserDetailsService userDetailsService;
  public SecurityConfig(JwtTokenProvider jwtTokenProvider,
                        UserDetailsService userDetailsService) {
    this.jwtTokenProvider = jwtTokenProvider;
    this.userDetailsService = userDetailsService;
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationProvider authenticationProvider() throws Exception {
    return new EsoftAuthenticationProvider(userDetailsService, passwordEncoder());
  }

//  @Bean
//  public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
//    return new JwtAuthenticationFilter(authenticationManager(), userDetailsService);
//  }

//  @Bean
//  public DaoAuthenticationProvider authenticationProvider() {
//    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//    authenticationProvider.setUserDetailsService(userDetailsService);
//    authenticationProvider.setPasswordEncoder(passwordEncoder());
//    return authenticationProvider;
//  }

//  @Bean
//  public AuthenticationManager authenticationManager(@Qualifier("authenticationProvider") AuthenticationProvider authenticationProvider) {
//    return new EsoftAuthenticationManager(authenticationProvider);
//  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
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
                    "/api/user/register",
                    "/v3/api-docs/**",
                    "/swagger-ui/**",
                    "/swagger-resources/**").permitAll() // Allow registration without authentication
            .anyRequest().authenticated() // Require authentication for other endpoints
            .and()
            .logout()
            .and()
            .addFilter(new JwtAuthenticationFilter(authenticationManager(), userDetailsService(), jwtTokenProvider))
            .addFilter(new JwtAuthorizationFilter(authenticationManager(), jwtTokenProvider))
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }
}