package com.cp.minigames.minicactpot.domain.model.aggregate;

import com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotGameStage;
import com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotNode;
import com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotSelection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Document
public class MiniCactpotAggregate {
    @Id
    private String id;

    private String createdDate;

    private MiniCactpotGameStage stage;

    private Integer winnings;

    private MiniCactpotSelection selection;

    private List<MiniCactpotNode> board;
}
