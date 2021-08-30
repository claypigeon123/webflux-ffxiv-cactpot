package com.cp.minigames.minicactpot.domain.model.request;

import com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotSelection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MakeMiniCactpotSelectionRequest {
    @NotNull(message = "Mini cactpot selection cannot be null")
    private MiniCactpotSelection selection;
}
