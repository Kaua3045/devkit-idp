package com.kaua.devkit.platform.domain.teams;

import com.kaua.devkit.platform.domain.Identifier;
import com.kaua.devkit.platform.domain.utils.ULID;

public record TeamMemberId(ULID value) implements Identifier<ULID> {

    public TeamMemberId {
        this.assertArgumentNotNull(value, "value", "id should not be null");
    }
}
