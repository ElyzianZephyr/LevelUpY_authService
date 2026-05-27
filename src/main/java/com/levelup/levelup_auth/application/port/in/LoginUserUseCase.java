package com.levelup.levelup_auth.application.port.in;

public interface LoginUserUseCase {
    JwtTokenPair login(LoginUserCommand command);
}