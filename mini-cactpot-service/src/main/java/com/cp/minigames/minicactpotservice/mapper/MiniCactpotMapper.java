package com.cp.minigames.minicactpotservice.mapper;

import com.cp.minigames.minicactpot.domain.model.aggregate.MiniCactpotAggregate;
import com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotNode;
import com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotPublicNode;
import com.cp.minigames.minicactpot.domain.model.dto.MiniCactpotTicketDto;
import com.cp.minigames.minicactpotservice.util.RandomNumberGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MiniCactpotMapper {
    private final RandomNumberGenerator rng;

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

    public MiniCactpotTicketDto mapDtoFromAggregate(MiniCactpotAggregate aggregate) {
        return MiniCactpotTicketDto.builder()
            .id(aggregate.getId())
            .board(mapPrivateBoardToPublic(aggregate.getBoard()))
            .winnings(aggregate.getWinnings())
            .stage(aggregate.getStage())
            .createdDate(aggregate.getCreatedDate())
            .updatedDate(aggregate.getUpdatedDate())
            .build();
    }
}
