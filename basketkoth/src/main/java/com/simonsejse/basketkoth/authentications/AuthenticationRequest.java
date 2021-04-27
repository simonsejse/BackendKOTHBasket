package com.simonsejse.basketkoth.authentications;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthenticationRequest {
    private final String username;
    private final String password;
}
