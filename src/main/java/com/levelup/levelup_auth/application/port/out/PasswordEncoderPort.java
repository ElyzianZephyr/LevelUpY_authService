package com.levelup.levelup_auth.application.port.out;

import com.levelup.levelup_auth.domain.model.user.Password;

public interface PasswordEncoderPort {
    Password encode(String rawPassword);
    boolean matches(String rawPassword, Password encodedPassword);
}