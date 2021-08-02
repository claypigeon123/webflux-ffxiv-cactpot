package com.cp.minigames.minicactpotservice.model.attributes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MiniCactpotBoard {
    private List<MiniCactpotNode> board;
}
