package com.esoft.auth.controller;

import com.esoft.auth.model.User;
import com.esoft.auth.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@SecurityRequirement(name = "esoft-api")
@RequestMapping("/api")
public class UserController {

  @Autowired
  private UserService userService;

  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@RequestBody User user) {
    // You can add validation and registration logic here
    // For example, you might want to check if the user already exists
    // and then save the user to the database

    userService.registerUser(user);

    return ResponseEntity.ok("User registered successfully");
  }
}
