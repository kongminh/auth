package com.esoft.auth.service;

import com.esoft.auth.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService extends PrimaryBaseService {

  private final Map<String, User> userDatabase = new HashMap<>();

  public void registerUser(User user) {
    // Here, you can implement the logic to save the user to the database
    // You might want to hash the password before saving it
    // Example: save the user to an in-memory map (for demonstration purposes)

    // Hash the password (you should use a secure hashing algorithm in production)
    String hashedPassword = hashPassword(user.getPassword());

    // Update the user's password with the hashed version
    user.setPassword(hashedPassword);

    // Save the user to the in-memory database
    userDatabase.put(user.getUsername(), user);
  }

  private String hashPassword(String password) {
    // Replace this with your actual password hashing implementation
    // For security, use a strong and proven hashing algorithm like BCrypt
    return password; // For demonstration purposes, we're using the original password as the "hashed" password
  }
}
