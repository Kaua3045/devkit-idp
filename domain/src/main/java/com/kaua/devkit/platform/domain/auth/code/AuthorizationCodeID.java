package com.kaua.devkit.platform.domain.auth.code;

import com.kaua.devkit.platform.domain.Identifier;
import com.kaua.devkit.platform.domain.utils.ULID;

public record AuthorizationCodeID(ULID value) implements Identifier<ULID> {

    public AuthorizationCodeID {
        this.assertArgumentNotNull(value, "value", "cannot be null");
    }
}
