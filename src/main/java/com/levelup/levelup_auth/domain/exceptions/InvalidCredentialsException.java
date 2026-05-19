package com.levelup.levelup_auth.domain.exceptions;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Неверный логин или пароль");
    }
}
