package com.levelup.levelup_auth.application.port.in.profile;

public interface UpdateProfileUseCase {
    UserProfileResult update(UpdateProfileCommand command);
}