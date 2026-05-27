package com.levelup.levelup_auth.infrastructure.persistence.mapper;

import com.levelup.levelup_auth.domain.model.Email;
import com.levelup.levelup_auth.domain.model.Login;
import com.levelup.levelup_auth.domain.model.Name;
import com.levelup.levelup_auth.domain.model.Password;
import com.levelup.levelup_auth.domain.model.User;
import com.levelup.levelup_auth.domain.model.UserId;
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

        return new User(
                new UserId(entity.getId()),
                new Login(entity.getLogin()),
                new Password(entity.getPasswordHash()),
                entity.getEmail() != null ? new Email(entity.getEmail()) : null,
                entity.getName() != null ? new Name(entity.getName()) : null
        );
    }
}