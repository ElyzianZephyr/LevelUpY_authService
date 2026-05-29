package com.levelup.levelup_auth.application.services;

import com.levelup.levelup_auth.application.port.in.JwtTokenPair;
import com.levelup.levelup_auth.application.port.in.login.LoginUserCommand;
import com.levelup.levelup_auth.application.port.in.login.LoginUserUseCase;
import com.levelup.levelup_auth.application.port.out.PasswordEncoderPort;
import com.levelup.levelup_auth.application.port.out.TokenGeneratorPort;
import com.levelup.levelup_auth.domain.exceptions.InvalidCredentialsException;
import com.levelup.levelup_auth.domain.model.user.Login;
import com.levelup.levelup_auth.domain.model.User;
import com.levelup.levelup_auth.domain.port.out.UserRepositoryPort;

public class LoginUserService implements LoginUserUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;
    private final TokenGeneratorPort tokenGeneratorPort;

    public LoginUserService(
            UserRepositoryPort userRepositoryPort,
            PasswordEncoderPort passwordEncoderPort,
            TokenGeneratorPort tokenGeneratorPort) {
        this.userRepositoryPort = userRepositoryPort;
        this.passwordEncoderPort = passwordEncoderPort;
        this.tokenGeneratorPort = tokenGeneratorPort;
    }

    @Override
    public JwtTokenPair login(LoginUserCommand command) {
        Login login = new Login(command.login());

        User user = userRepositoryPort.findByLogin(login)
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoderPort.matches(command.rawPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        String accessToken = tokenGeneratorPort.generateAccessToken(user);
        String refreshToken = tokenGeneratorPort.generateRefreshToken(user);

        return new JwtTokenPair(accessToken, refreshToken);
    }
}