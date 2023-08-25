package com.esoft.auth.model;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class UserDTO {
  @Schema(example = "minhvc")
  private String username;

  @Schema(example = "123456")
  private String password;

  private String role = UserRole.USER.name();
  private String permissions = UserPermission.WRITE.name();

  public enum UserRole {
    USER,
    ADMIN
  }

  public enum UserPermission {
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
