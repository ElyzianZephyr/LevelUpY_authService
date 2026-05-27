package com.levelup.levelup_auth.infrastructure.config;

import com.levelup.levelup_auth.application.port.in.DeleteProfileUseCase;
import com.levelup.levelup_auth.application.port.in.GetProfileUseCase;
import com.levelup.levelup_auth.application.port.in.LoginUserUseCase;
import com.levelup.levelup_auth.application.port.in.RegisterUserUseCase;
import com.levelup.levelup_auth.application.port.in.UpdateProfileUseCase;
import com.levelup.levelup_auth.application.port.out.PasswordEncoderPort;
import com.levelup.levelup_auth.application.port.out.TokenGeneratorPort;
import com.levelup.levelup_auth.application.services.DeleteProfileService;
import com.levelup.levelup_auth.application.services.GetProfileService;
import com.levelup.levelup_auth.application.services.LoginUserService;
import com.levelup.levelup_auth.application.services.RegisterUserService;
import com.levelup.levelup_auth.application.services.UpdateProfileService;
import com.levelup.levelup_auth.domain.port.out.UserRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public RegisterUserUseCase registerUserUseCase(
            UserRepositoryPort userRepositoryPort,
            PasswordEncoderPort passwordEncoderPort,
            TokenGeneratorPort tokenGeneratorPort) {
        return new RegisterUserService(userRepositoryPort, passwordEncoderPort, tokenGeneratorPort);
    }

    @Bean
    public LoginUserUseCase loginUserUseCase(
            UserRepositoryPort userRepositoryPort,
            PasswordEncoderPort passwordEncoderPort,
            TokenGeneratorPort tokenGeneratorPort) {
        return new LoginUserService(userRepositoryPort, passwordEncoderPort, tokenGeneratorPort);
    }

    @Bean
    public GetProfileUseCase getProfileUseCase(UserRepositoryPort userRepositoryPort) {
        return new GetProfileService(userRepositoryPort);
    }

    @Bean
    public UpdateProfileUseCase updateProfileUseCase(UserRepositoryPort userRepositoryPort) {
        return new UpdateProfileService(userRepositoryPort);
    }

    @Bean
    public DeleteProfileUseCase deleteProfileUseCase(UserRepositoryPort userRepositoryPort) {
        return new DeleteProfileService(userRepositoryPort);
    }
}