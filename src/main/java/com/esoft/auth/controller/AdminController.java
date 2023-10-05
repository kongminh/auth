package com.esoft.auth.controller;

import com.esoft.auth.model.UserDTO;
import com.esoft.auth.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin/user")
@SecurityRequirement(name = "esoft-api")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("")
    public ResponseEntity<String> createUser(@RequestBody UserDTO userDto) {
        boolean created = userService.createUser(userDto);
        if (created) {
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username existed");
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable Long userId, @RequestBody UserDTO userDto) {
        boolean updated = userService.updateUser(userId, userDto);
        if (updated) {
            return ResponseEntity.ok("User updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found userId");
        }
    }

    @DeleteMapping("/")
    public ResponseEntity<String> deleteUser(@RequestParam List<Long> userIds) {
        userService.deleteUsers(userIds);
        return ResponseEntity.ok("User deleted successfully");
    }
}
