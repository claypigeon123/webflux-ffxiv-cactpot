package com.cp.minigames.minicactpot.domain.model.aggregate;

import com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotGameStage;
import com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotNode;
import com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotSelection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MiniCactpotAggregate {
    private UUID id;

    private String createdDate;

    private MiniCactpotGameStage stage;

    private Integer winnings;

    private MiniCactpotSelection selection;

    private List<MiniCactpotNode> board;
}
