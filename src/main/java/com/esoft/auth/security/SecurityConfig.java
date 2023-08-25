package com.esoft.auth.security;

import com.esoft.auth.security.jwt.JwtAuthenticationFilter;
import com.esoft.auth.security.jwt.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private JwtTokenProvider tokenProvider;
  private EsoftUserDetailsService userDetailsService;

  public SecurityConfig(JwtTokenProvider tokenProvider, EsoftUserDetailsService userDetailsService) {
    this.tokenProvider = tokenProvider;
    this.userDetailsService = userDetailsService;
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManager() throws Exception {
    return super.authenticationManager();
  }

//  @Bean
//  public UserDetailsService userDetailsService() {
//    InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//    manager.createUser(
//        org.springframework.security.core.userdetails.User.withUsername("user")
//            .password("password")
//            .roles("USER")
//            .build());
//    return manager;
//  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
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
            .addFilter(new JwtAuthenticationFilter(authenticationManager(), userDetailsService))
            .addFilter(new JwtAuthorizationFilter(authenticationManager(), tokenProvider))
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

//  @Bean
//  public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
//    http.csrf().disable().authorizeRequests()
//            .antMatchers(
//                    "/",
//                    "/api/user/register",
//                    "/v3/api-docs/**",
//                    "/swagger-ui/**",
//                    "/swagger-resources/**")
//            .permitAll()
//            .anyRequest().authenticated()
//            .and()
//            .addFilter(new JwtAuthenticationFilter(authenticationManager(), userDetailsService))
//            .addFilter(new JwtAuthorizationFilter(authenticationManager(), tokenProvider))
//            .sessionManagement()
//            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//    return http.build();
//  }
}