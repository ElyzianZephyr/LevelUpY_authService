package com.levelup.levelup_auth.application.port.in;

public interface DeleteProfileUseCase {
    void deleteByLogin(String login);
}