package com.levelup.levelup_auth.domain.model;

import java.util.Objects;
import java.util.Optional;

public class User {
    private final UserId id;
    private Login login;
    private Password password;
    private Email email;
    private Name name;

    public User(UserId id, Login login, Password password) {
        this.id = Objects.requireNonNull(id);
        this.login = Objects.requireNonNull(login);
        this.password = Objects.requireNonNull(password);
    }

    public User(UserId id, Login login, Password password, Email email, Name name) {
        this.id = Objects.requireNonNull(id);
        this.login = Objects.requireNonNull(login);
        this.password = Objects.requireNonNull(password);
        this.email = email;
        this.name = name;
    }

    public void changeLogin(Login newLogin) {
        this.login = Objects.requireNonNull(newLogin);
    }

    public void changePassword(Password newPassword) {
        this.password = Objects.requireNonNull(newPassword);
    }

    public void changeEmail(Email newEmail) {
        this.email = Objects.requireNonNull(newEmail);
    }

    public void changeName(Name newName) {
        this.name = Objects.requireNonNull(newName);
    }

    public UserId getId() {
        return id;
    }

    public Login getLogin() {
        return login;
    }

    public Password getPassword() {
        return password;
    }

    public Optional<Email> getEmail() {
        return Optional.ofNullable(email);
    }

    public Optional<Name> getName() {
        return Optional.ofNullable(name);
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