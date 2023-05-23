package com.cp.minigames.minicactpotservice.repository.impl;

import com.cp.minigames.minicactpot.domain.model.aggregate.MiniCactpotAggregate;
import com.cp.minigames.minicactpotservice.repository.AbstractReactiveMongoRepository;
import com.cp.minigames.minicactpotservice.util.MongoQueryBuilder;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;

import static com.cp.minigames.minicactpot.domain.model.util.AggregateConstants.STAGE;
import static com.cp.minigames.minicactpot.domain.model.util.AggregateConstants.STAGE_NOT;

@Repository
public class MiniCactpotAggregateRepository extends AbstractReactiveMongoRepository<MiniCactpotAggregate> {
    private static final String COLLECTION_NAME = "mini-cactpot-aggregates";

    public MiniCactpotAggregateRepository(ReactiveMongoTemplate template) {
        super(MiniCactpotAggregate.class, COLLECTION_NAME, template);
    }

    @Override
    protected Query buildQuery(MultiValueMap<String, String> queryParams) {
        return MongoQueryBuilder.init(super.buildQuery(queryParams))
            .withInMatch(STAGE, queryParams.get(STAGE))
            .withNotInMatch(STAGE, queryParams.get(STAGE_NOT))
            .build();
    }
}
