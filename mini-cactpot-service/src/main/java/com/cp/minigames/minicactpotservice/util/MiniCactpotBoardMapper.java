package com.cp.minigames.minicactpotservice.util;

import com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotNode;
import com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotPublicNode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MiniCactpotBoardMapper {

    public List<MiniCactpotPublicNode> mapPrivateBoardToPublic(List<MiniCactpotNode> board) {
        List<MiniCactpotPublicNode> publicNodes = new ArrayList<>();

        for (MiniCactpotNode node : board) {
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

        return publicNodes;
    }
}
