package com.esoft.auth.model;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class UserDTO {
  @Schema(example = "minhvc")
  private String username;

  @Schema(example = "123456")
  private String password;

  private String role = Role.USER.name();
  private String permissions = Permission.WRITE.name();

  public enum Role {
    USER,
    ADMIN
  }

  public enum Permission {
    READ,
    WRITE
  }

  public UserDTO() {

  }

  public UserDTO(String username, String role, String permissions) {
    this.username = username;
    this.role = role;
    this.permissions = permissions;
  }
}
