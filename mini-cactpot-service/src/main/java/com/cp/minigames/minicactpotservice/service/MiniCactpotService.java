package com.cp.minigames.minicactpotservice.service;

import com.cp.minigames.minicactpot.domain.exception.*;
import com.cp.minigames.minicactpot.domain.model.aggregate.MiniCactpotAggregate;
import com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotGameStage;
import com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotNode;
import com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotSelection;
import com.cp.minigames.minicactpot.domain.model.request.MakeMiniCactpotSelectionRequest;
import com.cp.minigames.minicactpot.domain.model.request.ScratchMiniCactpotNodeRequest;
import com.cp.minigames.minicactpot.domain.model.response.GetMiniCactpotTicketResponse;
import com.cp.minigames.minicactpot.domain.model.response.MakeMiniCactpotSelectionResponse;
import com.cp.minigames.minicactpot.domain.model.response.ScratchMiniCactpotNodeResponse;
import com.cp.minigames.minicactpot.domain.model.response.StartMiniCactpotGameResponse;
import com.cp.minigames.minicactpotservice.config.props.MiniCactpotProperties;
import com.cp.minigames.minicactpotservice.repository.MiniCactpotAggregateRepository;
import com.cp.minigames.minicactpotservice.repository.base.ReactiveRepository;
import com.cp.minigames.minicactpotservice.util.MiniCactpotBoardUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class MiniCactpotService {
    private final ReactiveRepository<MiniCactpotAggregate, String> miniCactpotAggregateRepository;
    private final MiniCactpotProperties miniCactpotProperties;
    private final MiniCactpotBoardUtils miniCactpotBoardUtils;
    private final DateTimeFormatter dtf;
    private final Clock clock;

    public MiniCactpotService(
        MiniCactpotAggregateRepository miniCactpotAggregateRepository,
        MiniCactpotProperties miniCactpotProperties,
        MiniCactpotBoardUtils miniCactpotBoardUtils,
        DateTimeFormatter dtf,
        Clock clock
    ) {
        this.miniCactpotAggregateRepository = miniCactpotAggregateRepository;
        this.miniCactpotProperties = miniCactpotProperties;
        this.miniCactpotBoardUtils = miniCactpotBoardUtils;
        this.dtf = dtf;
        this.clock = clock;
    }

    public Mono<Map<Integer, Integer>> getWinningsMap() {
        return Mono.just(miniCactpotProperties.getWinningsMap());
    }

    public Flux<GetMiniCactpotTicketResponse> queryTickets(MultiValueMap<String, String> queryParams) {
        return miniCactpotAggregateRepository
            .query(queryParams)
            .map(saved -> GetMiniCactpotTicketResponse.builder()
                .id(saved.getId())
                .board(miniCactpotBoardUtils.mapPrivateBoardToPublic(saved.getBoard()))
                .winnings(saved.getWinnings())
                .stage(saved.getStage())
                .createdDate(saved.getCreatedDate())
                .build()
            );
    }

    public Mono<GetMiniCactpotTicketResponse> getTicket(String id) {
        return fetchGameBoard(id)
            .map(saved -> GetMiniCactpotTicketResponse.builder()
                .id(saved.getId())
                .board(miniCactpotBoardUtils.mapPrivateBoardToPublic(saved.getBoard()))
                .winnings(saved.getWinnings())
                .stage(saved.getStage())
                .createdDate(saved.getCreatedDate())
                .build()
            );
    }

    public Mono<StartMiniCactpotGameResponse> startGame() {
        return initializeNewGame()
            .flatMap(miniCactpotAggregateRepository::upsert)
            .map(saved -> StartMiniCactpotGameResponse.builder()
                .id(saved.getId())
                .board(miniCactpotBoardUtils.mapPrivateBoardToPublic(saved.getBoard()))
                .stage(saved.getStage())
                .build()
            );
    }

    public Mono<ScratchMiniCactpotNodeResponse> scratch(String id, ScratchMiniCactpotNodeRequest request) {
        return fetchGameBoard(id)
            .map(aggregate -> scratchMiniCactpotTicket(aggregate, request.getPosition()))
            .flatMap(miniCactpotAggregateRepository::upsert)
            .map(saved -> ScratchMiniCactpotNodeResponse.builder()
                .id(saved.getId())
                .board(miniCactpotBoardUtils.mapPrivateBoardToPublic(saved.getBoard()))
                .stage(saved.getStage())
                .build()
            );
    }

    public Mono<MakeMiniCactpotSelectionResponse> makeSelection(String id, MakeMiniCactpotSelectionRequest request) {
        return fetchGameBoard(id)
            .map(aggregate -> makeFinalSelection(aggregate, request.getSelection()))
            .flatMap(miniCactpotAggregateRepository::upsert)
            .map(saved -> MakeMiniCactpotSelectionResponse.builder()
                .id(saved.getId())
                .board(miniCactpotBoardUtils.mapPrivateBoardToPublic(saved.getBoard()))
                .stage(saved.getStage())
                .winnings(saved.getWinnings())
                .build()
            );

    }

    // ------------
    // Helpers
    // ------------

    private Mono<MiniCactpotAggregate> initializeNewGame() {
        List<MiniCactpotNode> board = miniCactpotBoardUtils.initializeNodes();
        return Mono.just(MiniCactpotAggregate.builder()
            .id(UUID.randomUUID().toString())
            .board(board)
            .selection(MiniCactpotSelection.NONE)
            .stage(MiniCactpotGameStage.SCRATCHING_FIRST)
            .winnings(null)
            .createdDate(OffsetDateTime.now(clock).format(dtf))
            .build()
        );
    }

    private Mono<MiniCactpotAggregate> fetchGameBoard(String id) {
        return miniCactpotAggregateRepository
            .findById(id)
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

        int winnings = miniCactpotProperties.getWinningsMap().get(sum);

        miniCactpotAggregate.setStage(stage.advance());
        miniCactpotAggregate.setSelection(selection);
        miniCactpotAggregate.setWinnings(winnings);
        return miniCactpotAggregate;
    }
}
