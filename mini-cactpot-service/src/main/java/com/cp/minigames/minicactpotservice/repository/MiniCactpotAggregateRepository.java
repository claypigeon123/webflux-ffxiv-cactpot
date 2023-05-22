package com.cp.minigames.minicactpotservice.repository;

import com.cp.minigames.minicactpot.domain.model.aggregate.MiniCactpotAggregate;
import com.cp.minigames.minicactpot.domain.model.util.AggregateConstants;
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

import java.util.List;
import java.util.Optional;

@Repository
public class MiniCactpotAggregateRepository implements ReactiveRepository<MiniCactpotAggregate, String> {

    private static final int MULTI_LIST_LIMIT = 5;

    private final Logger log = LoggerFactory.getLogger(MiniCactpotAggregateRepository.class);
    private final ReactiveCouchbaseTemplate template;

    public MiniCactpotAggregateRepository(ReactiveCouchbaseTemplate template) {
        this.template = template;
    }

    @Override
    public Flux<MiniCactpotAggregate> query(MultiValueMap<String, String> params) {
        Optional<String> createdDateFrom = params.getOrDefault(AggregateConstants.CREATED_DATE_FROM, List.of()).stream().findFirst();
        Optional<String> createdDateTo = params.getOrDefault(AggregateConstants.CREATED_DATE_TO, List.of()).stream().findFirst();
        Optional<List<String>> stages = Optional.ofNullable(params.get(AggregateConstants.STAGE));
        Optional<List<String>> stagesNot = Optional.ofNullable(params.get(AggregateConstants.STAGE_NOT));

        Query query = new Query();

        createdDateFrom.ifPresent(s -> query.addCriteria(QueryCriteria.where("STR_TO_UTC(" + AggregateConstants.CREATED_DATE + ")").gte(s)));

        createdDateTo.ifPresent(s -> query.addCriteria(QueryCriteria.where("STR_TO_UTC(" + AggregateConstants.CREATED_DATE + ")").lte(s)));

        stages.ifPresent(l -> l.stream()
            .limit(MULTI_LIST_LIMIT)
            .forEach(i -> query.addCriteria(QueryCriteria.where(AggregateConstants.STAGE).is(i)))
        );

        stagesNot.ifPresent(l -> l.stream()
            .limit(MULTI_LIST_LIMIT)
            .forEach(i -> query.addCriteria(QueryCriteria.where(AggregateConstants.STAGE).ne(i)))
        );

        log.debug("QUERY: {}", query.toN1qlSelectString(template, MiniCactpotAggregate.class, false));

        return template.findByQuery(MiniCactpotAggregate.class)
            .matching(query)
            .all();
    }

    @Override
    public Mono<MiniCactpotAggregate> findById(String id) {
        return template.findById(MiniCactpotAggregate.class)
            .one(id);
    }

    @Override
    public Mono<MiniCactpotAggregate> upsert(MiniCactpotAggregate aggregate) {
        return template.upsertById(MiniCactpotAggregate.class)
            .one(aggregate);
    }

    @Override
    public Mono<Void> delete(String id) {
        return template.removeById()
            .one(id)
            .then();
    }
}
