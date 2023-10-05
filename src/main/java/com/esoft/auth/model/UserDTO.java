package com.esoft.auth.model;

import com.esoft.auth.entity.user.UserEntity;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class UserDTO {
  private Long id;

  @Schema(example = "minhvc")
  private String username;

  @Schema(example = "123456")
  private String password;

  private String role = UserRole.ROLE_USER.name();
  private String permissions = UserPermission.WRITE.name();

  public enum UserRole {
    ROLE_USER,
    ROLE_ADMIN
  }

  public enum UserPermission {
    READ,
    WRITE
  }

  public UserDTO() {

  }

  public UserDTO(UserEntity userEntity) {
    this.id = userEntity.getId();
    this.username = userEntity.getUsername();
    this.role = userEntity.getRole();
    this.permissions = userEntity.getPermissions();
  }
}
