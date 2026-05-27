package com.levelup.levelup_auth.application.services;

import com.levelup.levelup_auth.application.port.in.JwtTokenPair;
import com.levelup.levelup_auth.application.port.in.RegisterUserCommand;
import com.levelup.levelup_auth.application.port.out.PasswordEncoderPort;
import com.levelup.levelup_auth.application.port.out.TokenGeneratorPort;
import com.levelup.levelup_auth.domain.exceptions.UserAlreadyExistsException;
import com.levelup.levelup_auth.domain.model.Login;
import com.levelup.levelup_auth.domain.model.Password;
import com.levelup.levelup_auth.domain.model.User;
import com.levelup.levelup_auth.domain.port.out.UserRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RegisterUserServiceTest {

    private UserRepositoryPort userRepositoryPort;
    private PasswordEncoderPort passwordEncoderPort;
    private TokenGeneratorPort tokenGeneratorPort;
    private RegisterUserService registerUserService;

    @BeforeEach
    void setUp() {
        userRepositoryPort = Mockito.mock(UserRepositoryPort.class);
        passwordEncoderPort = Mockito.mock(PasswordEncoderPort.class);
        tokenGeneratorPort = Mockito.mock(TokenGeneratorPort.class);
        registerUserService = new RegisterUserService(userRepositoryPort, passwordEncoderPort, tokenGeneratorPort);
    }

    @Test
    void shouldReturnJwtTokenPairWhenRegistrationIsSuccessful() {
        RegisterUserCommand command = new RegisterUserCommand("testUser", "rawPassword");
        Login login = new Login("testUser");
        Password encodedPassword = new Password("encodedPassword");

        when(userRepositoryPort.existsByLogin(login)).thenReturn(false);
        when(passwordEncoderPort.encode("rawPassword")).thenReturn(encodedPassword);
        when(tokenGeneratorPort.generateAccessToken(any(User.class))).thenReturn("access-token");
        when(tokenGeneratorPort.generateRefreshToken(any(User.class))).thenReturn("refresh-token");

        JwtTokenPair result = registerUserService.register(command);

        assertEquals("access-token", result.accessToken());
        assertEquals("refresh-token", result.refreshToken());
        verify(userRepositoryPort).save(any(User.class));
    }

    @Test
    void shouldThrowUserAlreadyExistsExceptionWhenLoginIsTaken() {
        RegisterUserCommand command = new RegisterUserCommand("testUser", "rawPassword");
        Login login = new Login("testUser");

        when(userRepositoryPort.existsByLogin(login)).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> registerUserService.register(command));
    }
}