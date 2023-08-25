package com.esoft.auth.controller;

import com.esoft.auth.entity.UserEntity;
import com.esoft.auth.model.UserDTO;
import com.esoft.auth.repository.UserRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
//@SecurityRequirement(name = "esoft-api")
@RequestMapping("/api/user")
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

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
}
