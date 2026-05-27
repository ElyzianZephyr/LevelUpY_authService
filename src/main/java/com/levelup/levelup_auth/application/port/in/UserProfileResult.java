package com.levelup.levelup_auth.application.port.in;

import java.util.UUID;

public record UserProfileResult(
        UUID id,
        String login,
        String email,
        String name
) {
}