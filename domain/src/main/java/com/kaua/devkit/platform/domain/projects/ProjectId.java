package com.kaua.devkit.platform.domain.projects;

import com.kaua.devkit.platform.domain.Identifier;
import com.kaua.devkit.platform.domain.utils.ULID;

public record ProjectId(ULID value) implements Identifier<ULID> {

    public ProjectId {
        this.assertArgumentNotNull(value, "value", "id should not be null");
    }
}
