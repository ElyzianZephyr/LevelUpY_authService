package com.levelup.levelup_auth.application.port.in.register;

import com.levelup.levelup_auth.application.port.in.JwtTokenPair;

public interface RegisterUserUseCase {
    JwtTokenPair register(RegisterUserCommand command);
}