package com.cp.minigames.minicactpot.domain.model.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record ScratchMiniCactpotNodeRequest(
    @NotNull
    @Min(value = 1, message = "Position to be scratched can't be less than 1")
    @Max(value = 9, message = "Position to be scratched can't be greater than 9")
    Integer position
) {
}
