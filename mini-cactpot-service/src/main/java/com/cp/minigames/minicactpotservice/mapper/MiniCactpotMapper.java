package com.cp.minigames.minicactpotservice.mapper;

import com.cp.minigames.minicactpot.domain.exception.impl.CannotMakeSelectionException;
import com.cp.minigames.minicactpot.domain.exception.impl.CannotScratchException;
import com.cp.minigames.minicactpot.domain.exception.impl.FieldAlreadyRevealedException;
import com.cp.minigames.minicactpot.domain.exception.impl.TicketAlreadyCompleteException;
import com.cp.minigames.minicactpot.domain.model.aggregate.MiniCactpotAggregate;
import com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotGameStage;
import com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotNode;
import com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotPublicNode;
import com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotSelection;
import com.cp.minigames.minicactpot.domain.model.dto.MiniCactpotTicketDto;
import com.cp.minigames.minicactpotservice.config.props.MiniCactpotProperties;
import com.cp.minigames.minicactpotservice.util.RandomNumberGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.*;

@Component
@RequiredArgsConstructor
public class MiniCactpotMapper {

    private final MiniCactpotProperties miniCactpotProperties;
    private final RandomNumberGenerator rng;
    private final Clock clock;

    public Mono<MiniCactpotAggregate> initializeNewGame(String ip) {
        List<MiniCactpotNode> board = initializeNodes();
        OffsetDateTime now = OffsetDateTime.now(clock);

        return Mono.just(MiniCactpotAggregate.builder()
            .id(UUID.randomUUID().toString())
            .ip(ip)
            .createdDate(now)
            .updatedDate(now)
            .board(board)
            .selection(MiniCactpotSelection.NONE)
            .stage(MiniCactpotGameStage.SCRATCHING_FIRST)
            .winnings(null)
            .build()
        );
    }

    public MiniCactpotAggregate scratchMiniCactpotTicket(MiniCactpotAggregate miniCactpotAggregate, Integer position) {
        MiniCactpotGameStage stage = miniCactpotAggregate.getStage();
        if (stage == MiniCactpotGameStage.DONE) {
            throw new TicketAlreadyCompleteException();
        }

        if (stage == MiniCactpotGameStage.SELECTING) {
            throw new CannotScratchException();
        }

        MiniCactpotNode node = miniCactpotAggregate.getBoard().get(position - 1);
        if (node.getIsRevealed()) {
            throw new FieldAlreadyRevealedException();
        }

        node.setIsRevealed(true);
        miniCactpotAggregate.getBoard().set(position - 1, node);
        miniCactpotAggregate.setStage(stage.advance());
        miniCactpotAggregate.setUpdatedDate(OffsetDateTime.now(clock));
        return miniCactpotAggregate;
    }

    public MiniCactpotAggregate makeSelection(MiniCactpotAggregate miniCactpotAggregate, MiniCactpotSelection selection) {
        MiniCactpotGameStage stage = miniCactpotAggregate.getStage();
        if (stage == MiniCactpotGameStage.DONE) {
            throw new TicketAlreadyCompleteException();
        }

        if (stage != MiniCactpotGameStage.SELECTING) {
            throw new CannotMakeSelectionException();
        }

        // Setting every node to be revealed
        for (MiniCactpotNode node : miniCactpotAggregate.getBoard()) {
            node.setIsRevealed(true);
        }

        // Calculating sum of selection
        int sum = Arrays.stream(selection.getPositions())
            .reduce(0, (left, right) -> left + miniCactpotAggregate.getBoard().get(right).getNumber());

        int winnings = miniCactpotProperties.winningsMap().get(sum);

        miniCactpotAggregate.setStage(stage.advance());
        miniCactpotAggregate.setSelection(selection);
        miniCactpotAggregate.setWinnings(winnings);
        miniCactpotAggregate.setUpdatedDate(OffsetDateTime.now(clock));
        return miniCactpotAggregate;
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

    public MiniCactpotTicketDto mapDtoFromAggregate(MiniCactpotAggregate aggregate) {
        return MiniCactpotTicketDto.builder()
            .id(aggregate.getId())
            .board(mapPrivateBoardToPublic(aggregate.getBoard()))
            .winnings(aggregate.getWinnings())
            .stage(aggregate.getStage())
            .selection(aggregate.getSelection())
            .createdDate(aggregate.getCreatedDate())
            .updatedDate(aggregate.getUpdatedDate())
            .build();
    }

    public List<MiniCactpotTicketDto> mapDtosFromAggregates(Collection<MiniCactpotAggregate> aggregates) {
        return aggregates.stream()
            .map(this::mapDtoFromAggregate)
            .toList();
    }

    // --

    private List<MiniCactpotNode> initializeNodes() {
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
