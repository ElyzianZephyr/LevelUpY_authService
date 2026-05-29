package com.levelup.levelup_auth.application.port.in.profile;

public interface GetProfileUseCase {
    UserProfileResult getByLogin(String login);
}