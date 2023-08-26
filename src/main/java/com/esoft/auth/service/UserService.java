package com.esoft.auth.service;

import com.esoft.auth.entity.UserEntity;
import com.esoft.auth.model.UserDTO;
import com.esoft.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService extends PrimaryBaseService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public boolean createUser(UserDTO userDto) {
    if (userRepository.existsByUsername(userDto.getUsername())) {
      return false;
    }
    UserEntity userEntity = new UserEntity();
    userEntity.setUsername(userDto.getUsername());
    userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
    userEntity.setRole(userDto.getRole());
    userEntity.setPermissions(userDto.getPermissions());
    userRepository.save(userEntity);
    return true;
  }

  public boolean updateUser(Long userId, UserDTO userDto) {
    if (userRepository.existsById(userId)) {
      UserEntity userEntity = new UserEntity();
      userEntity.setId(userId);
//      userEntity.setUsername(userDto.getUsername());
//      userEntity.setPassword(userDto.getPassword());
      userEntity.setRole(userDto.getRole());
      userEntity.setPermissions(userDto.getPermissions());
      userRepository.save(userEntity);
      return true;
    } else {
      return false;
    }
  }

  public void deleteUsers(List<Long> userIds) {
    userRepository.deleteAllById(userIds);
  }
}
