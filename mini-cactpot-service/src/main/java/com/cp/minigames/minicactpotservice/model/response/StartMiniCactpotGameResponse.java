package com.cp.minigames.minicactpotservice.model.response;

import com.cp.minigames.minicactpotservice.model.attributes.MiniCactpotGameStage;
import com.cp.minigames.minicactpotservice.model.attributes.MiniCactpotPublicNode;
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
public class StartMiniCactpotGameResponse {
    private UUID id;
    private List<MiniCactpotPublicNode> board;
    private MiniCactpotGameStage stage;
}
