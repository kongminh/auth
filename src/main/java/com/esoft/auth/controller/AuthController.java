package com.esoft.auth.controller;

import com.esoft.auth.model.UserDTO;
import com.esoft.auth.service.TokenBlacklistService;
import com.esoft.auth.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final UserService userService;
  private final TokenBlacklistService tokenBlacklistService;

  @Autowired
  public AuthController(UserService userService, TokenBlacklistService tokenBlacklistService) {
    this.userService = userService;
    this.tokenBlacklistService = tokenBlacklistService;
  }

  @GetMapping("authorize-data")
  @SecurityRequirement(name = "esoft-api")
  public UserDTO getAuthorizeData() {
    return userService.getUserInfo();
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

  @PostMapping("/register")
  public ResponseEntity<String> registerUser(@RequestBody UserDTO userDto) {
    boolean created = userService.createUser(userDto);
    if (created) {
      return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username existed");
    }
  }

  @PostMapping("/refresh")
  public ResponseEntity<?> refreshAccessToken(@RequestHeader("Refresh-Token") String refreshToken) {
    String newAccessToken = userService.createNewAccessToken(refreshToken);
    if (newAccessToken != null) {
      HttpHeaders headers = new HttpHeaders();
      headers.add("Authorization", newAccessToken);

      return ResponseEntity.ok().headers(headers).body("{\"Authorization\": \"" + newAccessToken + "\"}");
    } else {
      return ResponseEntity.badRequest().body("{\"ErrorMessage\": \"Invalid refresh token\"}");
    }
  }
}
