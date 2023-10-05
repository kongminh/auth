package com.esoft.auth.security.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginReq {
    @Schema(example = "minhvc")
    private String username;

    @Schema(example = "123456")
    private String password;

    private String roleType;
}
