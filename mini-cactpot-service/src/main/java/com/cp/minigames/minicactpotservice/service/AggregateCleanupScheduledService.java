package com.cp.minigames.minicactpotservice.service;

import com.cp.minigames.minicactpotservice.model.aggregate.MiniCactpotAggregate;
import com.cp.minigames.minicactpotservice.repository.MiniCactpotAggregateRepository;
import com.cp.minigames.minicactpotservice.repository.base.ReactiveRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@EnableAsync
public class AggregateCleanupScheduledService {
    private final Logger log = LoggerFactory.getLogger(AggregateCleanupScheduledService.class);

    private final ReactiveRepository<MiniCactpotAggregate, UUID> miniCactpotAggregateRepository;

    public AggregateCleanupScheduledService(MiniCactpotAggregateRepository miniCactpotAggregateRepository) {
        this.miniCactpotAggregateRepository = miniCactpotAggregateRepository;
    }

    @Async
    @Scheduled(cron = "${application.cron.aggregate-cleanup-cron}")
    public void performAggregateCleanup() {
        log.info("Starting aggregate cleanup job");
    }
}
