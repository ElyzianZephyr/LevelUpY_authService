package com.levelup.levelup_auth.application.port.in;

public interface RegisterUserUseCase {
    JwtTokenPair register(RegisterUserCommand command);
}