package com.cp.minigames.minicactpot.domain.model.util;

import lombok.Builder;

@Builder
public record Pagination (
    Long page,
    Long limit,
    Long total
) {
    public static Pagination fromQueryRes(long page, long limit, long total) {
        return Pagination.builder()
            .page(limit == 0 ? 1 : page)
            .limit(limit)
            .total(total)
            .build();
    }
}
