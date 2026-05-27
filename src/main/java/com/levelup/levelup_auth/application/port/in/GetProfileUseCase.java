package com.levelup.levelup_auth.application.port.in;

public interface GetProfileUseCase {
    UserProfileResult getByLogin(String login);
}