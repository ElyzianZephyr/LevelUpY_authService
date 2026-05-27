package com.levelup.levelup_auth.application.port.in;

public interface UpdateProfileUseCase {
    UserProfileResult update(UpdateProfileCommand command);
}