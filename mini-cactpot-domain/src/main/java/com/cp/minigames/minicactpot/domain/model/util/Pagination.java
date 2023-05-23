package com.cp.minigames.minicactpot.domain.model.util;

import lombok.Builder;

@Builder
public record Pagination (
    Long pageNumber,
    Long pageRecords,
    Long pageCount,
    Long recordCount
) {
    public static Pagination fromQueryRes(long page, long limit, long total, long found) {
        long pageCount = total % limit > 0
            ? Math.floorDiv(total, limit) + 1
            : Math.floorDiv(total, limit);

        return Pagination.builder()
            .pageNumber(limit == 0 ? 1 : page)
            .pageRecords(limit == 0 ? total : Math.min(limit, found))
            .pageCount(limit == 0 ? 1 : pageCount)
            .recordCount(total)
            .build();
    }
}
