package com.cp.minigames.minicactpotservice.util;

import com.cp.minigames.minicactpotservice.model.attributes.MiniCactpotBoard;
import com.cp.minigames.minicactpotservice.model.attributes.MiniCactpotNode;
import com.cp.minigames.minicactpotservice.model.attributes.MiniCactpotPublicBoard;
import com.cp.minigames.minicactpotservice.model.attributes.MiniCactpotPublicNode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MiniCactpotBoardMapper {

    public MiniCactpotPublicBoard mapPrivateBoardToPublic(MiniCactpotBoard board) {
        List<MiniCactpotPublicNode> publicNodes = new ArrayList<>();

        for (MiniCactpotNode node : board.getBoard()) {
            int number;
            if (node.getIsRevealed()) {
                number = node.getNumber();
            } else {
                number = -1;
            }
            publicNodes.add(MiniCactpotPublicNode.builder()
                .number(number)
                .build()
            );
        }

        return MiniCactpotPublicBoard.builder()
            .board(publicNodes)
            .build();
    }
}
