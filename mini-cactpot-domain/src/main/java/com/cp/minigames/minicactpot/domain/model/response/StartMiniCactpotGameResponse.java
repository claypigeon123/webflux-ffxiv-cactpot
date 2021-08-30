package com.cp.minigames.minicactpot.domain.model.response;

import com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotGameStage;
import com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotPublicNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StartMiniCactpotGameResponse {
    private String id;
    private List<MiniCactpotPublicNode> board;
    private MiniCactpotGameStage stage;
}
