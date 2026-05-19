package com.levelup.levelup_auth.domain.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String login) {
        super(String.format("Пользователь с логином '%s' уже зарегистрирован", login));
    }
}
