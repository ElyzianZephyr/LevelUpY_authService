package com.levelup.levelup_auth.domain.model;

import java.util.Objects;

public record Login(String value) {
    public Login {
        Objects.requireNonNull(value, "Логин не может быть null");
        if (value.isBlank()) {
            throw new IllegalArgumentException("Логин не может быть пустым");
        }
        if (value.length() < 3 || value.length() > 50) {
            throw new IllegalArgumentException("Длина логина должна быть от 3 до 50 символов");
        }

    }
}