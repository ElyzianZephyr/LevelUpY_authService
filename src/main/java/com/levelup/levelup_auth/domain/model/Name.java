package com.levelup.levelup_auth.domain.model;

import java.util.Objects;

public record Name(String value) {
    public Name {
        Objects.requireNonNull(value);
        if (value.isBlank() || value.length() > 100) {
            throw new IllegalArgumentException();
        }
    }
}