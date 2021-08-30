package com.cp.minigames.minicactpotservice.service;

import com.cp.minigames.minicactpotservice.config.properties.CleanupProperties;
import com.cp.minigames.minicactpot.domain.model.aggregate.MiniCactpotAggregate;
import com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotGameStage;
import com.cp.minigames.minicactpot.domain.model.util.MiniCactpotAggregateProperty;
import com.cp.minigames.minicactpotservice.repository.MiniCactpotAggregateRepository;
import com.cp.minigames.minicactpotservice.repository.base.ReactiveRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
@EnableAsync
public class AggregateCleanupScheduledService {
    private final Logger log = LoggerFactory.getLogger(AggregateCleanupScheduledService.class);

    private final ReactiveRepository<MiniCactpotAggregate, String> miniCactpotAggregateRepository;
    private final CleanupProperties cleanupProperties;
    private final Clock clock;
    private final DateTimeFormatter dtf;

    public AggregateCleanupScheduledService(
        MiniCactpotAggregateRepository miniCactpotAggregateRepository,
        CleanupProperties cleanupProperties,
        Clock clock,
        DateTimeFormatter dtf
    ) {
        this.miniCactpotAggregateRepository = miniCactpotAggregateRepository;
        this.cleanupProperties = cleanupProperties;
        this.clock = clock;
        this.dtf = dtf;
    }

    @Async
    @Scheduled(cron = "${application.cleanup.aggregate-cleanup-cron}")
    public void performAggregateCleanup() {
        log.info("Starting mini cactpot aggregate cleanup job");
        OffsetDateTime cutoff = OffsetDateTime.now(clock).minusHours(cleanupProperties.getCutoffHours());

        miniCactpotAggregateRepository.query(new LinkedMultiValueMap<>(Map.of(
            MiniCactpotAggregateProperty.CREATED_DATE_TO, List.of(cutoff.format(dtf)),
            MiniCactpotAggregateProperty.STAGE_NOT, List.of(MiniCactpotGameStage.DONE.toString())
        )))
            .map(MiniCactpotAggregate::getId)
            .flatMap(id -> miniCactpotAggregateRepository.delete(id).thenReturn(1))
            .reduce(0, Integer::sum)
            .map(sum -> {
                if (sum == 0) log.info("Found no aggregates to clean up");
                else log.info("Cleaned up {} mini cactpot aggregate" + (sum == 1 ? "" : "s"), sum);
                return sum;
            })
            .subscribe();
    }
}
