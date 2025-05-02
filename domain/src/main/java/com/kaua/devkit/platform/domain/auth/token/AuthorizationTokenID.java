package com.kaua.devkit.platform.domain.auth.token;

import com.kaua.devkit.platform.domain.Identifier;
import com.kaua.devkit.platform.domain.utils.ULID;

public record AuthorizationTokenID(ULID value) implements Identifier<ULID> {

    public AuthorizationTokenID {
        this.assertArgumentNotNull(value, "value", "cannot be null");
    }
}
