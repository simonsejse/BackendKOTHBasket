package com.simonsejse.basketkoth.authentications;

import com.simonsejse.basketkoth.security.JwtUtil;
import com.simonsejse.basketkoth.service.ApplicationUserService;
import com.simonsejse.basketkoth.user.ApplicationUser;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final ApplicationUserService applicationUserService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(AuthenticationRequest authenticationRequest){
        try{
            authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                      authenticationRequest.getUsername(), authenticationRequest.getPassword()
              )
            );
        }catch(BadCredentialsException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        final ApplicationUser applicationUser = applicationUserService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwtToken = jwtUtil.generateToken(applicationUser);
        final ResponseEntity<AuthenticationResponse> authenticationResponseResponseEntity
                = new ResponseEntity<>(new AuthenticationResponse(jwtToken), HttpStatus.OK);
        return authenticationResponseResponseEntity;
    }
}
