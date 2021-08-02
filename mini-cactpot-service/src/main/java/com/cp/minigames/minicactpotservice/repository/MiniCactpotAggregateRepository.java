package com.cp.minigames.minicactpotservice.repository;

import com.cp.minigames.minicactpotservice.model.aggregate.MiniCactpotAggregate;
import org.springframework.data.couchbase.repository.ReactiveCouchbaseRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MiniCactpotAggregateRepository extends ReactiveCouchbaseRepository<MiniCactpotAggregate, UUID> {

}
