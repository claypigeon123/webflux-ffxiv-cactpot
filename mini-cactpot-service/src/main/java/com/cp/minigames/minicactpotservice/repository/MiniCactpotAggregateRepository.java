package com.cp.minigames.minicactpotservice.repository;

import com.cp.minigames.minicactpotservice.model.aggregate.MiniCactpotAggregate;
import com.cp.minigames.minicactpotservice.repository.base.ReactiveRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.couchbase.core.ReactiveCouchbaseTemplate;
import org.springframework.data.couchbase.core.query.Query;
import org.springframework.data.couchbase.core.query.QueryCriteria;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;

@Repository
public class MiniCactpotAggregateRepository implements ReactiveRepository<MiniCactpotAggregate, UUID> {

    private final Logger log = LoggerFactory.getLogger(MiniCactpotAggregateRepository.class);
    private final ReactiveCouchbaseTemplate template;

    public MiniCactpotAggregateRepository(ReactiveCouchbaseTemplate template) {
        this.template = template;
    }

    @Override
    public Flux<MiniCactpotAggregate> query(MultiValueMap<String, String> queryParams) {
        Map<String, String> params = queryParams.toSingleValueMap();
        String createdDateFrom = params.get("createdDate.from");
        String createdDateTo = params.get("createdDate.to");
        String isDone = params.get("isDone");

        Query query = new Query();

        if (createdDateFrom != null) {
            query.addCriteria(QueryCriteria.where("STR_TO_UTC(createdDate)").gte(createdDateFrom));
        }
        if (createdDateTo != null) {
            query.addCriteria(QueryCriteria.where("STR_TO_UTC(createdDate)").lte(createdDateTo));
        }
        if (isDone != null) {
            query.addCriteria(QueryCriteria.where("isDone").is(Boolean.parseBoolean(isDone)));
        }

        log.debug("QUERY: {}", query.toN1qlSelectString(template, MiniCactpotAggregate.class, false));

        return template.findByQuery(MiniCactpotAggregate.class)
            .matching(query)
            .all();
    }

    @Override
    public Mono<MiniCactpotAggregate> findById(UUID uuid) {
        return template.findById(MiniCactpotAggregate.class)
            .one(uuid.toString());
    }

    @Override
    public Mono<MiniCactpotAggregate> upsert(MiniCactpotAggregate aggregate) {
        return template.upsertById(MiniCactpotAggregate.class)
            .one(aggregate);
    }

    @Override
    public Mono<Void> delete(UUID uuid) {
        return template.removeById()
            .one(uuid.toString())
            .then();
    }
}
