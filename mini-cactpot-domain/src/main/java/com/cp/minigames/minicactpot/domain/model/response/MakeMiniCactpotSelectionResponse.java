package com.cp.minigames.minicactpot.domain.model.response;

import com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotGameStage;
import com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotPublicNode;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Builder
@Jacksonized
public record MakeMiniCactpotSelectionResponse(
    String id,
    List<MiniCactpotPublicNode> board,
    MiniCactpotGameStage stage,
    Integer winnings
) {
}
