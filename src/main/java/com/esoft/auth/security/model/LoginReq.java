package com.esoft.auth.security.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginReq {
    private String username;

    private String password;

    private String roleType;
}
