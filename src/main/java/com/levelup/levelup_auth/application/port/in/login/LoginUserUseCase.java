package com.levelup.levelup_auth.application.port.in.login;

import com.levelup.levelup_auth.application.port.in.JwtTokenPair;

public interface LoginUserUseCase {
    JwtTokenPair login(LoginUserCommand command);
}