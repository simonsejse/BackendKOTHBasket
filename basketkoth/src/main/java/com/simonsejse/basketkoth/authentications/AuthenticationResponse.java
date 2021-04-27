package com.simonsejse.basketkoth.authentications;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthenticationResponse {
    private final String jwtToken;
}
