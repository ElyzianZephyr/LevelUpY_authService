package com.levelup.levelup_auth.application.services;

import com.levelup.levelup_auth.application.port.in.JwtTokenPair;
import com.levelup.levelup_auth.application.port.in.login.LoginUserCommand;
import com.levelup.levelup_auth.application.port.out.PasswordEncoderPort;
import com.levelup.levelup_auth.application.port.out.TokenGeneratorPort;
import com.levelup.levelup_auth.domain.exceptions.InvalidCredentialsException;
import com.levelup.levelup_auth.domain.model.user.Login;
import com.levelup.levelup_auth.domain.model.user.Password;
import com.levelup.levelup_auth.domain.model.User;
import com.levelup.levelup_auth.domain.model.user.UserId;
import com.levelup.levelup_auth.domain.port.out.UserRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class LoginUserServiceTest {

    private UserRepositoryPort userRepositoryPort;
    private PasswordEncoderPort passwordEncoderPort;
    private TokenGeneratorPort tokenGeneratorPort;
    private LoginUserService loginUserService;

    @BeforeEach
    void setUp() {
        userRepositoryPort = Mockito.mock(UserRepositoryPort.class);
        passwordEncoderPort = Mockito.mock(PasswordEncoderPort.class);
        tokenGeneratorPort = Mockito.mock(TokenGeneratorPort.class);
        loginUserService = new LoginUserService(userRepositoryPort, passwordEncoderPort, tokenGeneratorPort);
    }

    @Test
    void shouldReturnJwtTokenPairWhenCredentialsAreValid() {
        LoginUserCommand command = new LoginUserCommand("validUser", "validPassword");
        Login login = new Login("validUser");
        Password encodedPassword = new Password("encodedPassword");
        User user = new User(UserId.generate(), login, encodedPassword);

        when(userRepositoryPort.findByLogin(login)).thenReturn(Optional.of(user));
        when(passwordEncoderPort.matches("validPassword", encodedPassword)).thenReturn(true);
        when(tokenGeneratorPort.generateAccessToken(user)).thenReturn("access-token");
        when(tokenGeneratorPort.generateRefreshToken(user)).thenReturn("refresh-token");

        JwtTokenPair result = loginUserService.login(command);

        assertEquals("access-token", result.accessToken());
        assertEquals("refresh-token", result.refreshToken());
    }

    @Test
    void shouldThrowInvalidCredentialsExceptionWhenUserNotFound() {
        LoginUserCommand command = new LoginUserCommand("unknownUser", "anyPassword");
        Login login = new Login("unknownUser");

        when(userRepositoryPort.findByLogin(login)).thenReturn(Optional.empty());

        assertThrows(InvalidCredentialsException.class, () -> loginUserService.login(command));
    }

    @Test
    void shouldThrowInvalidCredentialsExceptionWhenPasswordIsIncorrect() {
        LoginUserCommand command = new LoginUserCommand("validUser", "wrongPassword");
        Login login = new Login("validUser");
        Password encodedPassword = new Password("encodedPassword");
        User user = new User(UserId.generate(), login, encodedPassword);

        when(userRepositoryPort.findByLogin(login)).thenReturn(Optional.of(user));
        when(passwordEncoderPort.matches("wrongPassword", encodedPassword)).thenReturn(false);

        assertThrows(InvalidCredentialsException.class, () -> loginUserService.login(command));
    }
}