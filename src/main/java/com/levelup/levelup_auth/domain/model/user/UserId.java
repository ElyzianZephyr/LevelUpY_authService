package com.levelup.levelup_auth.domain.model.user;

import java.util.Objects;
import java.util.UUID;

public record UserId(UUID value) {
    public   UserId{
        Objects.requireNonNull(value,"Идентификатор не может быть null");
    }

    //TODO
    public static   UserId generate() {
        return new UserId(UUID.randomUUID());
    }


}
