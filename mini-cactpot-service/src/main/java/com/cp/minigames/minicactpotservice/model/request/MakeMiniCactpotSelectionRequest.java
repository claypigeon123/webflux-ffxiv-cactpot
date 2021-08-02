package com.cp.minigames.minicactpotservice.model.request;

import com.cp.minigames.minicactpotservice.model.attributes.MiniCactpotSelection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MakeMiniCactpotSelectionRequest {
    @NotNull(message = "Mini cactpot ticket ID cannot be null")
    private UUID id;

    @NotNull(message = "Mini cactpot selection cannot be null")
    private MiniCactpotSelection selection;
}
