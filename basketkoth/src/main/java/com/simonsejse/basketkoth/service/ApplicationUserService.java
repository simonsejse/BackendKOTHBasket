package com.simonsejse.basketkoth.service;

import com.simonsejse.basketkoth.models.ApplicationUserRepository;
import com.simonsejse.basketkoth.user.ApplicationUser;
import com.simonsejse.basketkoth.token.ConfirmationToken;
import com.simonsejse.basketkoth.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.simonsejse.basketkoth.security.Error.*;

@Service
@AllArgsConstructor
public class ApplicationUserService implements UserDetailsService {

    private final ApplicationUserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public ApplicationUser loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findApplicationUserByUsername(username).orElseThrow(() -> {
            throw new UsernameNotFoundException(String.format(USERNAME_NOT_FOUND_MSG, username));
        });
    }



    public String signUpUser(ApplicationUser applicationUser) {
        final boolean emailExists = userRepository.findApplicationUserByEmail(applicationUser.getEmail()).isPresent();
        if (emailExists) {
            throw new IllegalStateException(String.format(EMAIL_TAKEN_MSG, applicationUser.getEmail()));
        }
        final boolean usernameExists = userRepository.findApplicationUserByUsername(applicationUser.getUsername()).isPresent();
        if (usernameExists) {
            throw new IllegalStateException(String.format(USERNAME_TAKEN_MSG, applicationUser.getUsername()));
        }
        final String encryptedPassword = bCryptPasswordEncoder.encode(applicationUser.getPassword());
        applicationUser.setPassword(encryptedPassword);

        userRepository.save(applicationUser);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                applicationUser
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        return token;
    }

    public void enableApplicationUser(Long id) {
        userRepository.findApplicationUserById(id).get().setEnabled(true);
    }
}
