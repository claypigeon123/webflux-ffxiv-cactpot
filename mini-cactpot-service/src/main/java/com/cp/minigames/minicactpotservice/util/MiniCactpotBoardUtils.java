package com.cp.minigames.minicactpotservice.util;

import com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotNode;
import com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotPublicNode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class MiniCactpotBoardUtils {

    private final RandomNumberGenerator rng;

    public MiniCactpotBoardUtils(RandomNumberGenerator rng) {
        this.rng = rng;
    }

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

    public List<MiniCactpotNode> initializeNodes() {
        List<MiniCactpotNode> nodes = new ArrayList<>();
        int initialRevealed = rng.generate(1, 9);
        for (int i = 1; i <= 9; i++) {
            MiniCactpotNode node = MiniCactpotNode.builder()
                .number(i)
                .isRevealed(false)
                .build();
            if (i == initialRevealed) {
                node.setIsRevealed(true);
            }
            nodes.add(node);
        }

        Collections.shuffle(nodes);
        return nodes;
    }
}
