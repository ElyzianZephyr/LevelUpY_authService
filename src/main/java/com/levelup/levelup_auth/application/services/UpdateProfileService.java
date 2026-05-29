package com.levelup.levelup_auth.application.services;

import com.levelup.levelup_auth.application.port.in.profile.UpdateProfileCommand;
import com.levelup.levelup_auth.application.port.in.profile.UpdateProfileUseCase;
import com.levelup.levelup_auth.application.port.in.profile.UserProfileResult;
import com.levelup.levelup_auth.domain.exceptions.UserNotFoundException;
import com.levelup.levelup_auth.domain.model.user.Email;
import com.levelup.levelup_auth.domain.model.user.Login;
import com.levelup.levelup_auth.domain.model.user.Name;
import com.levelup.levelup_auth.domain.model.User;
import com.levelup.levelup_auth.domain.port.out.UserRepositoryPort;

public class UpdateProfileService implements UpdateProfileUseCase {

    private final UserRepositoryPort userRepositoryPort;

    public UpdateProfileService(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public UserProfileResult update(UpdateProfileCommand command) {
        User user = userRepositoryPort.findByLogin(new Login(command.targetLogin()))
                .orElseThrow(() -> new UserNotFoundException(command.targetLogin()));

        if (command.email() != null && !command.email().isBlank()) {
            user.changeEmail(new Email(command.email()));
        }

        if (command.name() != null && !command.name().isBlank()) {
            user.changeName(new Name(command.name()));
        }

        userRepositoryPort.save(user);

        return new UserProfileResult(
                user.getId().value(),
                user.getLogin().value(),
                user.getEmail().map(Email::value).orElse(null),
                user.getName().map(Name::value).orElse(null)
        );
    }
}