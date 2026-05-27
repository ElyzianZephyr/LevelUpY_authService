package com.levelup.levelup_auth.application.port.out;

import com.levelup.levelup_auth.domain.model.User;

public interface TokenGeneratorPort {
    String generateAccessToken(User user);
    String generateRefreshToken(User user);
}