package com.cp.minigames.minicactpotservice.controller;

import com.cp.minigames.minicactpotservice.model.request.MakeMiniCactpotSelectionRequest;
import com.cp.minigames.minicactpotservice.model.request.ScratchMiniCactpotNodeRequest;
import com.cp.minigames.minicactpotservice.model.response.GetMiniCactpotTicketResponse;
import com.cp.minigames.minicactpotservice.model.response.MakeMiniCactpotSelectionResponse;
import com.cp.minigames.minicactpotservice.model.response.ScratchMiniCactpotNodeResponse;
import com.cp.minigames.minicactpotservice.model.response.StartMiniCactpotGameResponse;
import com.cp.minigames.minicactpotservice.service.MiniCactpotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/mini-cactpot")
public class MiniCactpotController {
    private final Logger log = LoggerFactory.getLogger(MiniCactpotController.class);

    private final MiniCactpotService miniCactpotService;

    public MiniCactpotController(MiniCactpotService miniCactpotService) {
        this.miniCactpotService = miniCactpotService;
    }

    @GetMapping("/winnings")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Map<Integer, Integer>> getWinningsMap() {
        log.info("Request to retrieve winnings map for mini cactpot");
        return miniCactpotService.getWinningsMap();
    }

    @GetMapping("/tickets")
    @ResponseStatus(HttpStatus.OK)
    public Flux<GetMiniCactpotTicketResponse> queryTickets(
        @RequestParam(required = false) MultiValueMap<String, String> queryParams
    ) {
        log.info("Request to query mini cactpot tickets by {}", queryParams);
        return miniCactpotService.queryTickets(queryParams);
    }

    @GetMapping("/tickets/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<GetMiniCactpotTicketResponse> getTicket(
        @PathVariable UUID id
    ) {
        log.info("Request to retrieve mini cactpot ticket by id {}", id);
        return miniCactpotService.getTicket(id);
    }

    @PostMapping(value = "/start-new-game", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Mono<StartMiniCactpotGameResponse> startGame() {
        log.info("Request to start new mini cactpot game");
        return miniCactpotService.startGame();
    }

    @PostMapping(value = "/scratch", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Mono<ScratchMiniCactpotNodeResponse> scratch(
        @Valid @RequestBody ScratchMiniCactpotNodeRequest request
    ) {
        log.info("Request to scratch position number {} on mini cactpot ticket ID {}", request.getPosition(), request.getId());
        return miniCactpotService.scratch(request);
    }

    @PostMapping(value = "/make-selection", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Mono<MakeMiniCactpotSelectionResponse> makeSelection(
        @Valid @RequestBody MakeMiniCactpotSelectionRequest request
    ) {
        log.info("Request to select {} on mini cactpot ticket ID {}", request.getSelection(), request.getId());
        return miniCactpotService.makeSelection(request);
    }
}
