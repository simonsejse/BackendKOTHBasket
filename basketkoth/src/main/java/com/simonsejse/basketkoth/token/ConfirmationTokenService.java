package com.simonsejse.basketkoth.token;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken confirmationToken){
        confirmationTokenRepository.save(confirmationToken);
    }
    
    public Optional<ConfirmationToken> getToken(String token){
        return confirmationTokenRepository.findConfirmationTokenByToken(token);
    }

    public void setConfirmedAt(String token) {
        confirmationTokenRepository
                .findConfirmationTokenByToken(token)
                .get()
                .setConfirmedAt(LocalDateTime.now());
    }
}
