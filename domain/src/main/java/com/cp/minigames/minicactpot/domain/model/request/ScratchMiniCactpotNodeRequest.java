package com.cp.minigames.minicactpotservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScratchMiniCactpotNodeRequest {
    @NotNull
    @Min(value = 1, message = "Position to be scratched can't be less than 1")
    @Max(value = 9, message = "Position to be scratched can't be greater than 9")
    private Integer position;
}
