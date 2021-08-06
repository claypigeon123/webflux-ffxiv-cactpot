package com.cp.minigames.minicactpotservice.service;

import com.cp.minigames.minicactpotservice.config.properties.CleanupProperties;
import com.cp.minigames.minicactpotservice.model.aggregate.MiniCactpotAggregate;
import com.cp.minigames.minicactpotservice.model.attributes.MiniCactpotGameStage;
import com.cp.minigames.minicactpotservice.repository.base.ReactiveRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@EnableAsync
public class AggregateCleanupScheduledService {
    private final Logger log = LoggerFactory.getLogger(AggregateCleanupScheduledService.class);

    private final ReactiveRepository<MiniCactpotAggregate, UUID> miniCactpotAggregateRepository;
    private final CleanupProperties cleanupProperties;
    private final Clock clock;
    private final DateTimeFormatter dtf;

    public AggregateCleanupScheduledService(ReactiveRepository<MiniCactpotAggregate, UUID> miniCactpotAggregateRepository, CleanupProperties cleanupProperties, Clock clock, DateTimeFormatter dtf) {
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
            "createdDate.to", List.of(cutoff.format(dtf)),
            "stage.not", List.of("DONE")
        )))
            .map(MiniCactpotAggregate::getId)
            .flatMap(id -> miniCactpotAggregateRepository.delete(id).thenReturn(1))
            .reduce(0, Integer::sum)
            .map(sum -> {
                if (sum == 0) log.info("Found no aggregates to clean up");
                else if (sum == 1) log.info("Cleaned up {} mini cactpot aggregate", sum);
                else if (sum > 1) log.info("Cleaned up {} mini cactpot aggregates", sum);
                return sum;
            })
            .subscribe();
    }
}
