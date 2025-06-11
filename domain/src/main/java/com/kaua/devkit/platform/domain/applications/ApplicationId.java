package com.kaua.devkit.platform.domain.applications;

import com.kaua.devkit.platform.domain.Identifier;
import com.kaua.devkit.platform.domain.utils.ULID;

public record ApplicationId(ULID value) implements Identifier<ULID> {

    public ApplicationId {
        this.assertArgumentNotNull(value, "value", "id should not be null");
    }
}
