package com.levelup.levelup_auth.presentation.rest.controller;

import com.levelup.levelup_auth.application.port.in.JwtTokenPair;
import com.levelup.levelup_auth.application.port.in.LoginUserCommand;
import com.levelup.levelup_auth.application.port.in.LoginUserUseCase;
import com.levelup.levelup_auth.application.port.in.RegisterUserCommand;
import com.levelup.levelup_auth.application.port.in.RegisterUserUseCase;
import com.levelup.levelup_auth.presentation.rest.dto.SignInRequest;
import com.levelup.levelup_auth.presentation.rest.dto.SignUpRequest;
import com.levelup.levelup_auth.presentation.rest.dto.TokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUserUseCase loginUserUseCase;

    public AuthController(RegisterUserUseCase registerUserUseCase, LoginUserUseCase loginUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
        this.loginUserUseCase = loginUserUseCase;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<TokenResponse> signUp(@RequestBody SignUpRequest request) {
        RegisterUserCommand command = new RegisterUserCommand(request.login(), request.password());
        JwtTokenPair tokenPair = registerUserUseCase.register(command);
        TokenResponse response = new TokenResponse(tokenPair.accessToken(), tokenPair.refreshToken());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<TokenResponse> signIn(@RequestBody SignInRequest request) {
        LoginUserCommand command = new LoginUserCommand(request.login(), request.password());
        JwtTokenPair tokenPair = loginUserUseCase.login(command);
        TokenResponse response = new TokenResponse(tokenPair.accessToken(), tokenPair.refreshToken());
        return ResponseEntity.ok(response);
    }
}
