package com.cp.minigames.minicactpotservice.service;

import com.cp.minigames.minicactpot.domain.model.aggregate.MiniCactpotAggregate;
import com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotGameStage;
import com.cp.minigames.minicactpot.domain.model.util.AggregateConstants;
import com.cp.minigames.minicactpot.domain.util.QueryParamsBuilder;
import com.cp.minigames.minicactpotservice.config.props.CleanupProperties;
import com.cp.minigames.minicactpotservice.repository.ReactiveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class AggregateCleanupScheduledService {

    private final ReactiveRepository<MiniCactpotAggregate> miniCactpotAggregateRepository;
    private final CleanupProperties cleanupProperties;
    private final Clock clock;

    @Async
    @Scheduled(cron = "${mini-cactpot.cleanup.aggregate-cleanup-cron}")
    public void performAggregateCleanup() {
        log.info("Starting mini cactpot aggregate cleanup job");
        OffsetDateTime cutoff = OffsetDateTime.now(clock).minusHours(cleanupProperties.cutoffHours());

        MultiValueMap<String, String> query = QueryParamsBuilder.init()
            .query(AggregateConstants.CREATED_DATE_TO, cutoff.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of(ZoneOffset.UTC.getId()))))
            .query(AggregateConstants.STAGE_NOT, MiniCactpotGameStage.DONE.toString())
            .buildMultiValueMap();

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
