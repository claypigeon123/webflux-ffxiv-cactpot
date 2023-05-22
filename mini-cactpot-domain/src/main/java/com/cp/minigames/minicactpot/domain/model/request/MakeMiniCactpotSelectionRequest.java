package com.cp.minigames.minicactpot.domain.model.request;

import com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotSelection;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record MakeMiniCactpotSelectionRequest(
    @NotNull(message = "Mini cactpot selection cannot be null") MiniCactpotSelection selection
) {
}
