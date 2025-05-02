package com.kaua.devkit.platform.domain.pagination;

import com.kaua.devkit.platform.domain.utils.Period;

import java.util.Optional;

public record SearchQuery(
        int page,
        int perPage,
        String terms,
        String sort,
        String direction,
        Period period
) {

    public SearchQuery(
            int page,
            int perPage,
            String terms,
            String sort,
            String direction
    ) {
        this(page, perPage, terms, sort, direction, null);
    }

    public Optional<Period> getPeriod() {
        return Optional.ofNullable(period);
    }
}
