package com.cp.minigames.minicactpot.domain.model.response;

import com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotGameStage;
import com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotPublicNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Builder
@Jacksonized
public record StartMiniCactpotGameResponse(
    String id,
    List<MiniCactpotPublicNode> board,
    MiniCactpotGameStage stage
) {
}
