package com.levelup.levelup_auth.domain.port.out;

import com.levelup.levelup_auth.domain.model.User;
import com.levelup.levelup_auth.domain.model.user.UserId;
import com.levelup.levelup_auth.domain.model.user.Login;
import java.util.Optional;

public interface UserRepositoryPort {
    Optional<User> findById(UserId id);
    Optional<User> findByLogin(Login login);
    boolean existsByLogin(Login login);
    void save(User user);
    void delete(UserId id);
}