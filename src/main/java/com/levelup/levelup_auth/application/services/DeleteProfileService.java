package com.levelup.levelup_auth.application.services;

import com.levelup.levelup_auth.application.port.in.DeleteProfileUseCase;
import com.levelup.levelup_auth.domain.exceptions.UserNotFoundException;
import com.levelup.levelup_auth.domain.model.Login;
import com.levelup.levelup_auth.domain.model.User;
import com.levelup.levelup_auth.domain.port.out.UserRepositoryPort;

public class DeleteProfileService implements DeleteProfileUseCase {

    private final UserRepositoryPort userRepositoryPort;

    public DeleteProfileService(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public void deleteByLogin(String login) {
        User user = userRepositoryPort.findByLogin(new Login(login))
                .orElseThrow(() -> new UserNotFoundException(login));

        userRepositoryPort.delete(user.getId());
    }
}