package com.levelup.levelup_auth.domain.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String login) {
        super(String.format("Пользователь '%s' не найден", login));
    }
}
