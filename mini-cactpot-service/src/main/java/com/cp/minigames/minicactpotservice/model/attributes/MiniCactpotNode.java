package com.cp.minigames.minicactpotservice.model.attributes;

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
