package com.cp.minigames.minicactpot.domain.model.aggregate;

import com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotGameStage;
import com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotNode;
import com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotSelection;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@SuperBuilder
@Jacksonized
@NoArgsConstructor // needed to avoid MappingException
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MiniCactpotAggregate extends Aggregate {
    private MiniCactpotGameStage stage;
    private Integer winnings;
    private MiniCactpotSelection selection;
    private List<MiniCactpotNode> board;
}
