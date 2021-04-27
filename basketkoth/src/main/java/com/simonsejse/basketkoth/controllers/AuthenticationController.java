package com.simonsejse.basketkoth.controllers;

import com.simonsejse.basketkoth.authentications.AuthenticationRequest;
import com.simonsejse.basketkoth.authentications.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value="authentication/api/v1/")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @RequestMapping({"/hello"})
    public String hello(){
        return "Hello API!";
    }

    @PostMapping(value="authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest){
        return this.authenticationService.createAuthenticationToken(authenticationRequest);
    }

}
