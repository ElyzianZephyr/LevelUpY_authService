package com.levelup.levelup_auth.application.services;

import com.levelup.levelup_auth.application.port.in.JwtTokenPair;
import com.levelup.levelup_auth.application.port.in.RegisterUserCommand;
import com.levelup.levelup_auth.application.port.in.RegisterUserUseCase;
import com.levelup.levelup_auth.application.port.out.PasswordEncoderPort;
import com.levelup.levelup_auth.application.port.out.TokenGeneratorPort;
import com.levelup.levelup_auth.domain.exceptions.UserAlreadyExistsException;
import com.levelup.levelup_auth.domain.model.Login;
import com.levelup.levelup_auth.domain.model.Password;
import com.levelup.levelup_auth.domain.model.User;
import com.levelup.levelup_auth.domain.model.UserId;
import com.levelup.levelup_auth.domain.port.out.UserRepositoryPort;

public class RegisterUserService implements RegisterUserUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;
    private final TokenGeneratorPort tokenGeneratorPort;

    public RegisterUserService(
            UserRepositoryPort userRepositoryPort,
            PasswordEncoderPort passwordEncoderPort,
            TokenGeneratorPort tokenGeneratorPort) {
        this.userRepositoryPort = userRepositoryPort;
        this.passwordEncoderPort = passwordEncoderPort;
        this.tokenGeneratorPort = tokenGeneratorPort;
    }

    @Override
    public JwtTokenPair register(RegisterUserCommand command) {
        Login login = new Login(command.login());

        if (userRepositoryPort.existsByLogin(login)) {
            throw new UserAlreadyExistsException(login.value());
        }

        Password encodedPassword = passwordEncoderPort.encode(command.rawPassword());
        User newUser = new User(UserId.generate(), login, encodedPassword);

        userRepositoryPort.save(newUser);

        String accessToken = tokenGeneratorPort.generateAccessToken(newUser);
        String refreshToken = tokenGeneratorPort.generateRefreshToken(newUser);

        return new JwtTokenPair(accessToken, refreshToken);
    }
}