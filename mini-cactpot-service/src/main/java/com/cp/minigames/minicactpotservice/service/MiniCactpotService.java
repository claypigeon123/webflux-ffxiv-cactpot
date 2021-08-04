package com.cp.minigames.minicactpotservice.service;

import com.cp.minigames.minicactpotservice.config.model.MiniCactpotProperties;
import com.cp.minigames.minicactpotservice.exception.MiniCactpotGameException;
import com.cp.minigames.minicactpotservice.model.aggregate.MiniCactpotAggregate;
import com.cp.minigames.minicactpotservice.model.attributes.MiniCactpotNode;
import com.cp.minigames.minicactpotservice.model.attributes.MiniCactpotPublicNode;
import com.cp.minigames.minicactpotservice.model.attributes.MiniCactpotSelection;
import com.cp.minigames.minicactpotservice.model.request.MakeMiniCactpotSelectionRequest;
import com.cp.minigames.minicactpotservice.model.request.ScratchMiniCactpotNodeRequest;
import com.cp.minigames.minicactpotservice.model.response.GetMiniCactpotTicketResponse;
import com.cp.minigames.minicactpotservice.model.response.MakeMiniCactpotSelectionResponse;
import com.cp.minigames.minicactpotservice.model.response.ScratchMiniCactpotNodeResponse;
import com.cp.minigames.minicactpotservice.model.response.StartMiniCactpotGameResponse;
import com.cp.minigames.minicactpotservice.repository.MiniCactpotAggregateRepository;
import com.cp.minigames.minicactpotservice.repository.base.ReactiveRepository;
import com.cp.minigames.minicactpotservice.util.MiniCactpotBoardMapper;
import com.cp.minigames.minicactpotservice.util.RandomNumberGenerator;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class MiniCactpotService {
    private final ReactiveRepository<MiniCactpotAggregate, UUID> miniCactpotAggregateRepository;
    private final MiniCactpotProperties miniCactpotProperties;
    private final MiniCactpotBoardMapper miniCactpotBoardMapper;
    private final RandomNumberGenerator rng;
    private final DateTimeFormatter dtf;

    public MiniCactpotService(
        MiniCactpotAggregateRepository miniCactpotAggregateRepository,
        MiniCactpotProperties miniCactpotProperties,
        MiniCactpotBoardMapper miniCactpotBoardMapper,
        RandomNumberGenerator rng,
        DateTimeFormatter dtf
    ) {
        this.miniCactpotAggregateRepository = miniCactpotAggregateRepository;
        this.miniCactpotProperties = miniCactpotProperties;
        this.miniCactpotBoardMapper = miniCactpotBoardMapper;
        this.rng = rng;
        this.dtf = dtf;
    }

    public Mono<Map<Integer, Integer>> getWinningsMap() {
        return Mono.just(miniCactpotProperties.getWinningsMap());
    }

    public Flux<GetMiniCactpotTicketResponse> queryTickets(MultiValueMap<String, String> queryParams) {
        return miniCactpotAggregateRepository
            .query(queryParams)
            .map(saved -> GetMiniCactpotTicketResponse.builder()
                .id(saved.getId())
                .board(miniCactpotBoardMapper.mapPrivateBoardToPublic(saved.getBoard()))
                .winnings(saved.getWinnings())
                .isDone(saved.getIsDone())
                .nScratched(saved.getNScratched())
                .createdDate(saved.getCreatedDate())
                .build()
            );
    }

    public Mono<GetMiniCactpotTicketResponse> getTicket(UUID id) {
        return fetchGameBoard(id)
            .map(saved -> GetMiniCactpotTicketResponse.builder()
                .id(saved.getId())
                .board(miniCactpotBoardMapper.mapPrivateBoardToPublic(saved.getBoard()))
                .winnings(saved.getWinnings())
                .isDone(saved.getIsDone())
                .nScratched(saved.getNScratched())
                .createdDate(saved.getCreatedDate())
                .build()
            );
    }

    public Mono<StartMiniCactpotGameResponse> startGame() {
        return initializeNewGame()
            .flatMap(miniCactpotAggregateRepository::upsert)
            .map(saved -> StartMiniCactpotGameResponse.builder()
                .id(saved.getId())
                .board(miniCactpotBoardMapper.mapPrivateBoardToPublic(saved.getBoard()))
                .build()
            );
    }

    public Mono<ScratchMiniCactpotNodeResponse> scratch(ScratchMiniCactpotNodeRequest request) {
        return fetchGameBoard(request.getId())
            .map(aggregate -> scratchMiniCactpotTicket(aggregate, request.getPosition()))
            .flatMap(miniCactpotAggregateRepository::upsert)
            .map(saved -> ScratchMiniCactpotNodeResponse.builder()
                .id(saved.getId())
                .board(miniCactpotBoardMapper.mapPrivateBoardToPublic(saved.getBoard()))
                .build()
            );
    }

    public Mono<MakeMiniCactpotSelectionResponse> makeSelection(MakeMiniCactpotSelectionRequest request) {
        return fetchGameBoard(request.getId())
            .map(aggregate -> makeFinalSelection(aggregate, request.getSelection()))
            .flatMap(miniCactpotAggregateRepository::upsert)
            .map(saved -> MakeMiniCactpotSelectionResponse.builder()
                .id(saved.getId())
                .board(miniCactpotBoardMapper.mapPrivateBoardToPublic(saved.getBoard()))
                .winnings(saved.getWinnings())
                .build()
            );

    }

    // ------------
    // Helpers
    // ------------

    private Mono<MiniCactpotAggregate> initializeNewGame() {
        List<MiniCactpotNode> board = initializeNodes();
        return Mono.just(MiniCactpotAggregate.builder()
            .board(board)
            .nScratched(0)
            .selection(MiniCactpotSelection.NONE)
            .canSelect(false)
            .isDone(false)
            .winnings(null)
            .createdDate(OffsetDateTime.now().format(dtf))
            .build()
        );
    }

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

    private Mono<MiniCactpotAggregate> fetchGameBoard(UUID id) {
        return miniCactpotAggregateRepository
            .findById(id)
            .switchIfEmpty(Mono.error(new MiniCactpotGameException("Mini cactpot ticket with ID " + id + " not found")));
    }

    private MiniCactpotAggregate scratchMiniCactpotTicket(MiniCactpotAggregate miniCactpotAggregate, Integer position) {
        if (miniCactpotAggregate.getIsDone()) {
            throw new MiniCactpotGameException("The given mini cactpot ticket has already been completed");
        }

        if (miniCactpotAggregate.getNScratched() >= 3) {
            throw new MiniCactpotGameException("Cannot scrach any more fields on the given ticket");
        }

        MiniCactpotNode node = miniCactpotAggregate.getBoard().get(position - 1);
        if (node.getIsRevealed()) {
            throw new MiniCactpotGameException("The given field is already revealed on the given ticket");
        }

        node.setIsRevealed(true);
        miniCactpotAggregate.getBoard().set(position - 1, node);
        miniCactpotAggregate.setNScratched(miniCactpotAggregate.getNScratched() + 1);
        if (miniCactpotAggregate.getNScratched() == 3) {
            miniCactpotAggregate.setCanSelect(true);
        }
        return miniCactpotAggregate;
    }

    private MiniCactpotAggregate makeFinalSelection(MiniCactpotAggregate miniCactpotAggregate, MiniCactpotSelection selection) {
        if (miniCactpotAggregate.getIsDone()) {
            throw new MiniCactpotGameException("The given mini cactpot ticket has already been completed");
        }

        if (!miniCactpotAggregate.getCanSelect() || miniCactpotAggregate.getNScratched() != 3) {
            throw new MiniCactpotGameException("Cannot make a selection on the given ticket yet");
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

        miniCactpotAggregate.setCanSelect(false);
        miniCactpotAggregate.setIsDone(true);
        miniCactpotAggregate.setSelection(selection);
        miniCactpotAggregate.setWinnings(winnings);
        return miniCactpotAggregate;
    }
}
