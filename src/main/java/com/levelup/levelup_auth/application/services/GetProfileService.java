package com.levelup.levelup_auth.application.services;

import com.levelup.levelup_auth.application.port.in.GetProfileUseCase;
import com.levelup.levelup_auth.application.port.in.UserProfileResult;
import com.levelup.levelup_auth.domain.exceptions.UserNotFoundException;
import com.levelup.levelup_auth.domain.model.Email;
import com.levelup.levelup_auth.domain.model.Login;
import com.levelup.levelup_auth.domain.model.Name;
import com.levelup.levelup_auth.domain.model.User;
import com.levelup.levelup_auth.domain.port.out.UserRepositoryPort;

public class GetProfileService implements GetProfileUseCase {

    private final UserRepositoryPort userRepositoryPort;

    public GetProfileService(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public UserProfileResult getByLogin(String login) {
        User user = userRepositoryPort.findByLogin(new Login(login))
                .orElseThrow(() -> new UserNotFoundException(login));

        return new UserProfileResult(
                user.getId().value(),
                user.getLogin().value(),
                user.getEmail().map(Email::value).orElse(null),
                user.getName().map(Name::value).orElse(null)
        );
    }
}