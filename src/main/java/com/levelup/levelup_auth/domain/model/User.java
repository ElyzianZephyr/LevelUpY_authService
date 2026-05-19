package com.levelup.levelup_auth.domain.model;

import java.util.Objects;

public class User {
    private final UserId id;
    private Login login;
    private Password password;

    // Конструктор гарантирует, что объект нельзя создать в некорректном состоянии
    public User(UserId id, Login login, Password password) {
        this.id = Objects.requireNonNull(id, "User ID не может быть null");
        this.login = Objects.requireNonNull(login, "Login не может быть null");
        this.password = Objects.requireNonNull(password, "Password не может быть null");
    }

    // Бизнес-метод для изменения логина (вместо бездумного сеттера)
    public void changeLogin(Login newLogin) {
        this.login = Objects.requireNonNull(newLogin, "Новый login не может быть null");
    }

    // Бизнес-метод для смены пароля
    public void changePassword(Password newPassword) {
        this.password = Objects.requireNonNull(newPassword, "Новый password не может быть null");
    }

    // Разрешены только геттеры. Сеттеры отсутствуют для предотвращения неконтролируемого изменения состояния
    public UserId getId() {
        return id;
    }

    public Login getLogin() {
        return login;
    }

    public Password getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}