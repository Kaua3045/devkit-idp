package com.kaua.devkit.platform.domain.users;

import com.kaua.devkit.platform.domain.Identifier;
import com.kaua.devkit.platform.domain.utils.ULID;

public record UserID(ULID value) implements Identifier<ULID> {

    public UserID {
        this.assertArgumentNotNull(value, "value", "UserID cannot be null");
    }
}
