package com.esoft.auth.controller;

import com.esoft.auth.entity.UserEntity;
import com.esoft.auth.model.UserDTO;
import com.esoft.auth.repository.UserRepository;
import com.esoft.auth.security.JwtTokenProvider;
import com.esoft.auth.service.TokenBlacklistService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;
  private final UserDetailsService userDetailsService;
  private final TokenBlacklistService tokenBlacklistService;

  public AuthController(UserRepository userRepository,
                        PasswordEncoder passwordEncoder,
                        JwtTokenProvider jwtTokenProvider,
                        UserDetailsService userDetailsService,
                        TokenBlacklistService tokenBlacklistService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtTokenProvider = jwtTokenProvider;
    this.userDetailsService = userDetailsService;
    this.tokenBlacklistService = tokenBlacklistService;
  }

  @GetMapping("authorize-data")
  @SecurityRequirement(name = "esoft-api")
  public List<UserDTO> getAuthorizeData() {
    return userRepository.findAll().stream()
            .map(user -> new UserDTO(user.getUsername(), user.getRole(), user.getPermissions()))
            .collect(Collectors.toList());
  }

  @PostMapping("disable")
  @SecurityRequirement(name = "esoft-api")
  public ResponseEntity<String> disableToken(HttpServletRequest request) {
    String authorizationHeader = request.getHeader("Authorization");
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      String token = authorizationHeader.substring(7); // Extract token part
      tokenBlacklistService.blacklistToken(token);
      return ResponseEntity.ok("Token disabled");
    } else {
      return ResponseEntity.badRequest().body("Invalid or missing Bearer token");
    }
  }

  @GetMapping
  @SecurityRequirement(name = "esoft-api")
  public List<UserDTO> getAllUsers() {
    return userRepository.findAll().stream()
            .map(user -> new UserDTO(user.getUsername(), user.getRole(), user.getPermissions()))
            .collect(Collectors.toList());
  }

  @PostMapping("/register")
  public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {
    // Check if username already exists
    if (userRepository.existsByUsername(userDTO.getUsername())) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
    }

    // Create UserEntity from UserDTO
    UserEntity userEntity = new UserEntity();
    userEntity.setUsername(userDTO.getUsername());
    userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
    userEntity.setRole(userDTO.getRole());
    userEntity.setPermissions(userDTO.getPermissions());

    userRepository.save(userEntity);

    return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
  }

  @PostMapping("/refresh")
  public ResponseEntity<?> refreshAccessToken(@RequestHeader("Refresh-Token") String refreshToken) {
    if (jwtTokenProvider.validateRefreshToken(refreshToken)) {
      String username = jwtTokenProvider.getUsernameFromRefreshToken(refreshToken);
      UserDetails userDetails = userDetailsService.loadUserByUsername(username);
      Authentication authentication = new UsernamePasswordAuthenticationToken(
              userDetails, null, userDetails.getAuthorities()
      );

      String newAccessToken = jwtTokenProvider.generateToken(authentication);

      HttpHeaders headers = new HttpHeaders();
      headers.add("Authorization", newAccessToken);

      return ResponseEntity.ok().headers(headers).body("{\"Authorization\": \"" + newAccessToken + "\"}");
    } else {
      return ResponseEntity.badRequest().body("{\"ErrorMessage\": \"Invalid refresh token\"}");
    }
  }
}
