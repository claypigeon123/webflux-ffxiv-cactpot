package com.cp.minigames.minicactpotservice.controller;

import com.cp.minigames.minicactpot.domain.model.dto.MiniCactpotTicketDto;
import com.cp.minigames.minicactpot.domain.model.request.MakeMiniCactpotSelectionRequest;
import com.cp.minigames.minicactpot.domain.model.request.ScratchMiniCactpotNodeRequest;
import com.cp.minigames.minicactpot.domain.model.response.PaginatedResponse;
import com.cp.minigames.minicactpotservice.service.MiniCactpotService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mini-cactpot")
public class MiniCactpotController {

    private final MiniCactpotService miniCactpotService;

    @GetMapping("/winnings")
    public Mono<Map<Integer, Integer>> getWinningsMap() {
        log.debug("Request to retrieve winnings map for mini cactpot");
        return miniCactpotService.getWinningsMap();
    }

    @GetMapping("/tickets")
    public Mono<PaginatedResponse<MiniCactpotTicketDto>> queryTickets(
        @RequestParam(required = false) MultiValueMap<String, String> queryParams,
        @RequestParam(defaultValue = "1") @Min(1) Long page,
        @RequestParam(defaultValue = "32") @Min(0) Long limit
    ) {
        log.debug("Request to query mini cactpot tickets by {}", queryParams);
        return miniCactpotService.queryTickets(queryParams, page, limit);
    }

    @GetMapping("/tickets/{id}")
    public Mono<MiniCactpotTicketDto> getTicket(@PathVariable String id) {
        log.debug("Request to retrieve mini cactpot ticket by id {}", id);
        return miniCactpotService.getTicket(id);
    }

    @PostMapping(value = "/start-new-game")
    public Mono<MiniCactpotTicketDto> startGame() {
        log.debug("Request to start new mini cactpot game");
        return miniCactpotService.startGame();
    }

    @PostMapping(value = "/scratch/{id}")
    public Mono<MiniCactpotTicketDto> scratch(
        @PathVariable String id,
        @Valid @RequestBody ScratchMiniCactpotNodeRequest request
    ) {
        log.debug("Request to scratch position number {} on mini cactpot ticket ID {}", request.position(), id);
        return miniCactpotService.scratch(id, request);
    }

    @PostMapping(value = "/make-selection/{id}")
    public Mono<MiniCactpotTicketDto> makeSelection(
        @PathVariable String id,
        @Valid @RequestBody MakeMiniCactpotSelectionRequest request
    ) {
        log.debug("Request to select {} on mini cactpot ticket ID {}", request.selection(), id);
        return miniCactpotService.makeSelection(id, request);
    }
}
