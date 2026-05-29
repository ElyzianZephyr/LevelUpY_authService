package com.levelup.levelup_auth.application.port.in.profile;

import java.util.Objects;

public record UpdateProfileCommand(
        String targetLogin,
        String email,
        String name
) {
    public UpdateProfileCommand {
        Objects.requireNonNull(targetLogin);
        if (targetLogin.isBlank()) {
            throw new IllegalArgumentException();
        }
    }
}