package com.cp.minigames.minicactpot.domain.model.error;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record MiniCactpotError(
    String error
) {
}
