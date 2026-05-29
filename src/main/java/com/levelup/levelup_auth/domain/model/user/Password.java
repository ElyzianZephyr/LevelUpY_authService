package com.levelup.levelup_auth.domain.model.user;

import java.util.Objects;

public record Password(String hashedValue) {
    public Password {
        Objects.requireNonNull(hashedValue, "Хеш пароля не может быть null");
        if (hashedValue.isBlank()) {
            throw new IllegalArgumentException("Хеш пароля не может быть пустым");
        }
    }
}