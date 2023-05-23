package com.cp.minigames.minicactpotservice.service;

import com.cp.minigames.minicactpot.domain.exception.impl.*;
import com.cp.minigames.minicactpot.domain.model.aggregate.MiniCactpotAggregate;
import com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotGameStage;
import com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotNode;
import com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotSelection;
import com.cp.minigames.minicactpot.domain.model.dto.MiniCactpotTicketDto;
import com.cp.minigames.minicactpot.domain.model.request.MakeMiniCactpotSelectionRequest;
import com.cp.minigames.minicactpot.domain.model.request.ScratchMiniCactpotNodeRequest;
import com.cp.minigames.minicactpot.domain.model.response.MakeMiniCactpotSelectionResponse;
import com.cp.minigames.minicactpot.domain.model.response.PaginatedResponse;
import com.cp.minigames.minicactpot.domain.model.response.ScratchMiniCactpotNodeResponse;
import com.cp.minigames.minicactpot.domain.model.response.StartMiniCactpotGameResponse;
import com.cp.minigames.minicactpot.domain.model.util.Pagination;
import com.cp.minigames.minicactpotservice.config.props.MiniCactpotProperties;
import com.cp.minigames.minicactpotservice.mapper.MiniCactpotMapper;
import com.cp.minigames.minicactpotservice.repository.AbstractReactiveMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MiniCactpotService {

    private final AbstractReactiveMongoRepository<MiniCactpotAggregate> miniCactpotAggregateRepository;
    private final MiniCactpotProperties miniCactpotProperties;
    private final MiniCactpotMapper miniCactpotMapper;
    private final Clock clock;

    public Mono<Map<Integer, Integer>> getWinningsMap() {
        return Mono.just(miniCactpotProperties.winningsMap());
    }

    public Mono<PaginatedResponse<MiniCactpotTicketDto>> queryTickets(MultiValueMap<String, String> queryParams, long page, long limit) {
        return Mono.zip(
                miniCactpotAggregateRepository.query(queryParams, page, limit)
                    .map(miniCactpotMapper::mapDtoFromAggregate)
                    .collectList(),
                miniCactpotAggregateRepository.count(queryParams)
            )
            .map(tuple2 -> PaginatedResponse.<MiniCactpotTicketDto>builder()
                .documents(tuple2.getT1())
                .pagination(Pagination.fromQueryRes(page, limit, tuple2.getT2(), tuple2.getT1().size()))
                .build()
            );
    }

    public Mono<MiniCactpotTicketDto> getTicket(String id) {
        return fetchGameBoard(id)
            .map(miniCactpotMapper::mapDtoFromAggregate);
    }

    public Mono<StartMiniCactpotGameResponse> startGame() {
        return initializeNewGame()
            .flatMap(miniCactpotAggregateRepository::insert)
            .map(aggregate -> StartMiniCactpotGameResponse.builder()
                .id(aggregate.getId())
                .board(miniCactpotMapper.mapPrivateBoardToPublic(aggregate.getBoard()))
                .stage(aggregate.getStage())
                .build()
            );
    }

    public Mono<ScratchMiniCactpotNodeResponse> scratch(String id, ScratchMiniCactpotNodeRequest request) {
        return fetchGameBoard(id)
            .map(aggregate -> scratchMiniCactpotTicket(aggregate, request.position()))
            .flatMap(miniCactpotAggregateRepository::update)
            .map(saved -> ScratchMiniCactpotNodeResponse.builder()
                .id(saved.getId())
                .board(miniCactpotMapper.mapPrivateBoardToPublic(saved.getBoard()))
                .stage(saved.getStage())
                .build()
            );
    }

    public Mono<MakeMiniCactpotSelectionResponse> makeSelection(String id, MakeMiniCactpotSelectionRequest request) {
        return fetchGameBoard(id)
            .map(aggregate -> makeFinalSelection(aggregate, request.selection()))
            .flatMap(miniCactpotAggregateRepository::update)
            .map(saved -> MakeMiniCactpotSelectionResponse.builder()
                .id(saved.getId())
                .board(miniCactpotMapper.mapPrivateBoardToPublic(saved.getBoard()))
                .stage(saved.getStage())
                .winnings(saved.getWinnings())
                .build()
            );

    }

    // ------------
    // Helpers
    // ------------

    private Mono<MiniCactpotAggregate> initializeNewGame() {
        List<MiniCactpotNode> board = miniCactpotMapper.initializeNodes();
        OffsetDateTime now = OffsetDateTime.now(clock);

        return Mono.just(MiniCactpotAggregate.builder()
            .id(UUID.randomUUID().toString())
            .board(board)
            .selection(MiniCactpotSelection.NONE)
            .stage(MiniCactpotGameStage.SCRATCHING_FIRST)
            .winnings(null)
            .createdDate(now)
            .updatedDate(now)
            .build()
        );
    }

    private Mono<MiniCactpotAggregate> fetchGameBoard(String id) {
        return miniCactpotAggregateRepository.findById(id)
            .switchIfEmpty(Mono.error(new TicketNotFoundException(id)));
    }

    private MiniCactpotAggregate scratchMiniCactpotTicket(MiniCactpotAggregate miniCactpotAggregate, Integer position) {
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

    private MiniCactpotAggregate makeFinalSelection(MiniCactpotAggregate miniCactpotAggregate, MiniCactpotSelection selection) {
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
        int sum = 0;
        for (int pos : selection.getPositions()) {
            sum += miniCactpotAggregate.getBoard().get(pos).getNumber();
        }

        int winnings = miniCactpotProperties.winningsMap().get(sum);

        miniCactpotAggregate.setStage(stage.advance());
        miniCactpotAggregate.setSelection(selection);
        miniCactpotAggregate.setWinnings(winnings);
        miniCactpotAggregate.setUpdatedDate(OffsetDateTime.now(clock));
        return miniCactpotAggregate;
    }
}
