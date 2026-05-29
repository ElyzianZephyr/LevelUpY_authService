package com.levelup.levelup_auth.application.port.in.profile;

import java.util.UUID;

public record UserProfileResult(
        UUID id,
        String login,
        String email,
        String name
) {
}