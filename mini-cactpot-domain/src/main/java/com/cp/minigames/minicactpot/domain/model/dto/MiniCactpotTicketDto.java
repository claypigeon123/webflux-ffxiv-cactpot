package com.cp.minigames.minicactpot.domain.model.dto;

import com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotGameStage;
import com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotPublicNode;
import com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotSelection;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.time.OffsetDateTime;
import java.util.List;

@Builder
@Jacksonized
public record MiniCactpotTicketDto(
    String id,
    OffsetDateTime createdDate,
    OffsetDateTime updatedDate,
    MiniCactpotGameStage stage,
    Integer winnings,
    MiniCactpotSelection selection,
    List<MiniCactpotPublicNode> board
) {
}
