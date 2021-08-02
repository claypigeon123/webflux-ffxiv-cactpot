package com.cp.minigames.minicactpotservice.scheduled;

import com.cp.minigames.minicactpotservice.repository.MiniCactpotAggregateRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AggregateCleanupService {

    private final MiniCactpotAggregateRepository miniCactpotAggregateRepository;

    public AggregateCleanupService(MiniCactpotAggregateRepository miniCactpotAggregateRepository) {
        this.miniCactpotAggregateRepository = miniCactpotAggregateRepository;
    }

    @Scheduled
    public void performAggregateCleanup() {

    }
}
