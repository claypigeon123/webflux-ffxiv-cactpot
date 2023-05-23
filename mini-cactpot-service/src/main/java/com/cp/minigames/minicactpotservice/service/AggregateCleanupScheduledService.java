package com.cp.minigames.minicactpotservice.service;

import com.cp.minigames.minicactpot.domain.model.aggregate.MiniCactpotAggregate;
import com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotGameStage;
import com.cp.minigames.minicactpot.domain.model.util.AggregateConstants;
import com.cp.minigames.minicactpotservice.config.props.CleanupProperties;
import com.cp.minigames.minicactpotservice.repository.AbstractReactiveMongoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AggregateCleanupScheduledService {

    private final AbstractReactiveMongoRepository<MiniCactpotAggregate> miniCactpotAggregateRepository;
    private final CleanupProperties cleanupProperties;
    private final Clock clock;

    @Async
    @Scheduled(cron = "${application.cleanup.aggregate-cleanup-cron}")
    public void performAggregateCleanup() {
        log.info("Starting mini cactpot aggregate cleanup job");
        OffsetDateTime cutoff = OffsetDateTime.now(clock).minusHours(cleanupProperties.cutoffHours());

        var query = new LinkedMultiValueMap<>(Map.of(
            AggregateConstants.CREATED_DATE_TO, List.of(cutoff.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of(ZoneOffset.UTC.getId())))),
            AggregateConstants.STAGE_NOT, List.of(MiniCactpotGameStage.DONE.toString())
        ));

        miniCactpotAggregateRepository.query(query, 1L, 0L)
            .flatMap(aggregate -> miniCactpotAggregateRepository.deleteById(aggregate.getId()).thenReturn(1))
            .reduce(0, Integer::sum)
            .doOnNext(sum -> {
                if (sum == 0) log.info("Found no aggregates to clean up");
                else log.info("Cleaned up {} mini cactpot aggregate{}", sum, sum == 1 ? "" : "s");
            })
            .subscribe();
    }
}
