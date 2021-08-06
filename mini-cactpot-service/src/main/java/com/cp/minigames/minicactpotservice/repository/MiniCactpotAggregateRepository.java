package com.cp.minigames.minicactpotservice.repository;

import com.cp.minigames.minicactpotservice.model.aggregate.MiniCactpotAggregate;
import com.cp.minigames.minicactpotservice.repository.base.ReactiveRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.couchbase.core.ReactiveCouchbaseTemplate;
import org.springframework.data.couchbase.core.query.Query;
import org.springframework.data.couchbase.core.query.QueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@Repository
public class MiniCactpotAggregateRepository implements ReactiveRepository<MiniCactpotAggregate, UUID> {

    private static final int MULTI_LIST_LIMIT = 5;

    private final Logger log = LoggerFactory.getLogger(MiniCactpotAggregateRepository.class);
    private final ReactiveCouchbaseTemplate template;

    public MiniCactpotAggregateRepository(ReactiveCouchbaseTemplate template) {
        this.template = template;
    }

    @Override
    public Flux<MiniCactpotAggregate> query(MultiValueMap<String, String> params) {
        Optional<String> createdDateFrom = params.getOrDefault("createdDate.from", List.of()).stream().findFirst();
        Optional<String> createdDateTo = params.getOrDefault("createdDate.to", List.of()).stream().findFirst();
        List<String> stages = params.get("stage");
        List<String> stagesNot = params.get("stage.not");

        Query query = new Query();

        createdDateFrom.ifPresent(s -> query.addCriteria(QueryCriteria.where("STR_TO_UTC(createdDate)").gte(s)));

        createdDateTo.ifPresent(s -> query.addCriteria(QueryCriteria.where("STR_TO_UTC(createdDate)").lte(s)));

        if (stages != null) {
            for (int i = 0; i < Integer.min(stages.size(), MULTI_LIST_LIMIT); i++) {
                query.addCriteria(QueryCriteria.where("stage").is(stages.get(i)));
            }
        }
        if (stagesNot != null) {
            for (int i = 0; i < Integer.min(stagesNot.size(), MULTI_LIST_LIMIT); i++) {
                query.addCriteria(QueryCriteria.where("stage").ne(stagesNot.get(i)));
            }
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
