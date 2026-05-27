package com.levelup.levelup_auth.infrastructure.security;

import com.levelup.levelup_auth.application.port.out.PasswordEncoderPort;
import com.levelup.levelup_auth.domain.model.Password;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BcryptPasswordEncoderAdapter implements PasswordEncoderPort {

    private final PasswordEncoder passwordEncoder;

    public BcryptPasswordEncoderAdapter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Password encode(String rawPassword) {
        return new Password(passwordEncoder.encode(rawPassword));
    }

    @Override
    public boolean matches(String rawPassword, Password encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword.hashedValue());
    }
}