package com.levelup.levelup_auth.application.port.in.login;

import java.util.Objects;

public record LoginUserCommand(String login, String rawPassword) {
    public LoginUserCommand {
        Objects.requireNonNull(login);
        Objects.requireNonNull(rawPassword);
    }
}