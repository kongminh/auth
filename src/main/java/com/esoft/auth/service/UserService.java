package com.esoft.auth.service;

import com.esoft.auth.entity.UserEntity;
import com.esoft.auth.model.UserDTO;
import com.esoft.auth.repository.UserRepository;
import com.esoft.auth.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class UserService extends PrimaryBaseService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;
  private final UserDetailsService userDetailsService;

  @Autowired
  public UserService(UserRepository userRepository,
                     PasswordEncoder passwordEncoder,
                     JwtTokenProvider jwtTokenProvider,
                     UserDetailsService userDetailsService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtTokenProvider = jwtTokenProvider;
    this.userDetailsService = userDetailsService;
  }

  public UserDTO getUserInfo() {
    org.springframework.security.core.Authentication auth =
            SecurityContextHolder.getContext().getAuthentication();
    Optional<UserEntity> userEntityOpt = userRepository.findByUsername(auth.getName());
    if (!userEntityOpt.isPresent()) {
      return null;
    }
    return new UserDTO(userEntityOpt.get());
  }

  public List<UserDTO> getAllUsers() {
    return userRepository.findAll().stream()
            .map(UserDTO::new)
            .collect(Collectors.toList());
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
    Optional<UserEntity> userEntityOpt = userRepository.findById(userId);
    if (userEntityOpt.isPresent()) {
      UserEntity userEntity = userEntityOpt.get();
      userEntity.setId(userId);
//      userEntity.setUsername(userDto.getUsername());
      userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
      userEntity.setRole(userDto.getRole());
      userEntity.setPermissions(userDto.getPermissions());
      userRepository.save(userEntity);
      return true;
    } else {
      return false;
    }
  }

  public String createNewAccessToken(String refreshToken) {
    if (jwtTokenProvider.validateRefreshToken(refreshToken)) {
      String username = jwtTokenProvider.getUsernameFromRefreshToken(refreshToken);
      UserDetails userDetails = userDetailsService.loadUserByUsername(username);
      Authentication authentication = new UsernamePasswordAuthenticationToken(
              userDetails, null, userDetails.getAuthorities()
      );
      return jwtTokenProvider.generateToken(authentication);
    } else {
      return null;
    }
  }

  public void deleteUsers(List<Long> userIds) {
    userRepository.deleteAllById(userIds);
  }
}
