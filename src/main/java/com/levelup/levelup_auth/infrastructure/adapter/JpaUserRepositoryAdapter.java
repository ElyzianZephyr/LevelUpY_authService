package com.levelup.levelup_auth.infrastructure.adapter;

import com.levelup.levelup_auth.domain.model.Login;
import com.levelup.levelup_auth.domain.model.User;
import com.levelup.levelup_auth.domain.model.UserId;
import com.levelup.levelup_auth.domain.port.out.UserRepositoryPort;
import com.levelup.levelup_auth.infrastructure.persistence.entity.UserEntity;
import com.levelup.levelup_auth.infrastructure.persistence.mapper.UserMapper;
import com.levelup.levelup_auth.infrastructure.persistence.repository.UserJpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class JpaUserRepositoryAdapter implements UserRepositoryPort {

    private final UserJpaRepository jpaRepository;
    private final UserMapper mapper;

    public JpaUserRepositoryAdapter(UserJpaRepository jpaRepository, UserMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<User> findById(UserId id) {
        return jpaRepository.findById(id.value())
                .map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByLogin(Login login) {
        return jpaRepository.findByLogin(login.value())
                .map(mapper::toDomain);
    }

    @Override
    public boolean existsByLogin(Login login) {
        return jpaRepository.existsByLogin(login.value());
    }

    @Override
    public void save(User user) {
        UserEntity entity = mapper.toEntity(user);
        jpaRepository.save(entity);
    }

    @Override
    public void delete(UserId id) {
        jpaRepository.deleteById(id.value());
    }
}