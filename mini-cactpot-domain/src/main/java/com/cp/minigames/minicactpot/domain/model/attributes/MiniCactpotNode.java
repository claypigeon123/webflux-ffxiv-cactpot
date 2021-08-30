package com.cp.minigames.minicactpot.domain.model.attributes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MiniCactpotNode {
    private Integer number;
    private Boolean isRevealed;
}
