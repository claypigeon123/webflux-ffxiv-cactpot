package com.cp.minigames.minicactpot.domain.model.response;

import com.cp.minigames.minicactpot.domain.model.util.Pagination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedResponse<T> {
    private List<T> documents;
    private Pagination pagination;
}
