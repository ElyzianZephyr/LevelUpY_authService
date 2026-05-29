package com.levelup.levelup_auth.infrastructure.persistence.mapper;

import com.levelup.levelup_auth.domain.model.user.Email;
import com.levelup.levelup_auth.domain.model.user.Login;
import com.levelup.levelup_auth.domain.model.user.Name;
import com.levelup.levelup_auth.domain.model.user.Password;
import com.levelup.levelup_auth.domain.model.User;
import com.levelup.levelup_auth.domain.model.user.UserId;
import com.levelup.levelup_auth.infrastructure.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity toEntity(User user) {
        if (user == null) {
            return null;
        }

        return new UserEntity(
                user.getId().value(),
                user.getLogin().value(),
                user.getPassword().hashedValue(),
                user.getEmail().map(Email::value).orElse(null),
                user.getName().map(Name::value).orElse(null)
        );
    }

    public User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        return User.builder()
                .id(new UserId(entity.getId()))
                .login(new Login(entity.getLogin()))
                .password(new Password(entity.getPasswordHash()))
                .build();
    }
}