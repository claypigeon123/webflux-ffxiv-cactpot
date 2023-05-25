package com.cp.minigames.minicactpotservice.service;

import com.cp.minigames.minicactpot.domain.exception.impl.GameAlreadyInProgress;
import com.cp.minigames.minicactpot.domain.exception.impl.TicketNotFoundException;
import com.cp.minigames.minicactpot.domain.model.aggregate.MiniCactpotAggregate;
import com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotGameStage;
import com.cp.minigames.minicactpot.domain.model.dto.MiniCactpotTicketDto;
import com.cp.minigames.minicactpot.domain.model.request.MakeMiniCactpotSelectionRequest;
import com.cp.minigames.minicactpot.domain.model.request.ScratchMiniCactpotNodeRequest;
import com.cp.minigames.minicactpot.domain.model.response.PaginatedResponse;
import com.cp.minigames.minicactpot.domain.model.util.AggregateConstants;
import com.cp.minigames.minicactpot.domain.model.util.Pagination;
import com.cp.minigames.minicactpotservice.config.props.MiniCactpotProperties;
import com.cp.minigames.minicactpotservice.mapper.MiniCactpotMapper;
import com.cp.minigames.minicactpotservice.repository.ReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MiniCactpotService {

    private final ReactiveRepository<MiniCactpotAggregate> miniCactpotAggregateRepository;
    private final MiniCactpotProperties miniCactpotProperties;
    private final MiniCactpotMapper miniCactpotMapper;

    public Mono<Map<Integer, Integer>> getWinningsMap() {
        return Mono.just(miniCactpotProperties.winningsMap());
    }

    public Mono<PaginatedResponse<MiniCactpotTicketDto>> queryTickets(MultiValueMap<String, String> queryParams, long page, long limit) {
        return miniCactpotAggregateRepository.queryWithCount(queryParams, page, limit)
            .map(tuple2 -> tuple2.mapT1(miniCactpotMapper::mapDtosFromAggregates))
            .map(tuple2 -> PaginatedResponse.<MiniCactpotTicketDto>builder()
                .documents(tuple2.getT1())
                .pagination(Pagination.fromQueryRes(page, limit, tuple2.getT2()))
                .build()
            );
    }

    public Mono<MiniCactpotTicketDto> getTicket(String id) {
        return fetchGameBoard(id)
            .map(miniCactpotMapper::mapDtoFromAggregate);
    }

    public Mono<MiniCactpotTicketDto> startGame(String ip) {
        MultiValueMap<String, String> query = new LinkedMultiValueMap<>(Map.of(
            AggregateConstants.IP, List.of(ip),
            AggregateConstants.STAGE_NOT, List.of(MiniCactpotGameStage.DONE.name())
        ));

        return miniCactpotAggregateRepository.count(query)
            .flatMap(count -> count >= 3
                ? Mono.error(GameAlreadyInProgress::new)
                : Mono.empty()
            )
            .then(Mono.defer(() -> miniCactpotMapper.initializeNewGame(ip)))
            .flatMap(miniCactpotAggregateRepository::insert)
            .map(miniCactpotMapper::mapDtoFromAggregate);
    }

    public Mono<MiniCactpotTicketDto> scratch(String id, ScratchMiniCactpotNodeRequest request) {
        return fetchGameBoard(id)
            .map(aggregate -> miniCactpotMapper.scratchMiniCactpotTicket(aggregate, request.position()))
            .flatMap(miniCactpotAggregateRepository::update)
            .map(miniCactpotMapper::mapDtoFromAggregate);
    }

    public Mono<MiniCactpotTicketDto> makeSelection(String id, MakeMiniCactpotSelectionRequest request) {
        return fetchGameBoard(id)
            .map(aggregate -> miniCactpotMapper.makeSelection(aggregate, request.selection()))
            .flatMap(miniCactpotAggregateRepository::update)
            .map(miniCactpotMapper::mapDtoFromAggregate);

    }

    // ------------
    // Helpers
    // ------------

    private Mono<MiniCactpotAggregate> fetchGameBoard(String id) {
        return miniCactpotAggregateRepository.findById(id).switchIfEmpty(Mono.error(new TicketNotFoundException(id)));
    }
}
