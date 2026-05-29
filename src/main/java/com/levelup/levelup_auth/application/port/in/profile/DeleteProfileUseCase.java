package com.levelup.levelup_auth.application.port.in.profile;

public interface DeleteProfileUseCase {
    void deleteByLogin(String login);
}