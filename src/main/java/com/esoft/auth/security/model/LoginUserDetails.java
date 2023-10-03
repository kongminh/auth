package com.esoft.auth.security.model;

import com.esoft.auth.entity.user.UserEntity;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public class LoginUserDetails extends org.springframework.security.core.userdetails.User {

    private Long id;
    private String username;
    private String password;
    private String role;
    private UserEntity userInfo;
    private List<String> permissions;
    private Collection<GrantedAuthority> authorities;

    public LoginUserDetails(UserEntity user, Collection<GrantedAuthority> authorities) {
        super(user.getUsername(), user.getPassword() != null ? user.getPassword() : "", authorities);
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.authorities = authorities;
        this.userInfo = user;
    }

}
