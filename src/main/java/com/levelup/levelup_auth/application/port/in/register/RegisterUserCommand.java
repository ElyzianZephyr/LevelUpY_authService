package com.levelup.levelup_auth.application.port.in.register;

import java.util.Objects;

public record RegisterUserCommand(String login, String rawPassword) {
    public RegisterUserCommand {
        Objects.requireNonNull(login, "Логин обязателен");
        Objects.requireNonNull(rawPassword, "Пароль обязателен");

    }
}