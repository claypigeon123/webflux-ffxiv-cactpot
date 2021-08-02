package com.cp.minigames.minicactpotservice.model.response;

import com.cp.minigames.minicactpotservice.model.attributes.MiniCactpotPublicBoard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetMiniCactpotTicketResponse {
    private UUID id;
    private MiniCactpotPublicBoard board;
    private Integer nScratched;
    private Boolean isDone;
    private Integer winnings;
}
