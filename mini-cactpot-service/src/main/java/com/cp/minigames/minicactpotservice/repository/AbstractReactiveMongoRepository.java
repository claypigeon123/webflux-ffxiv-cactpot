package com.cp.minigames.minicactpotservice.repository;

import com.cp.minigames.minicactpot.domain.model.aggregate.Aggregate;
import com.cp.minigames.minicactpotservice.util.MongoQueryBuilder;
import com.mongodb.client.result.DeleteResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.FindAndReplaceOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.List;

import static com.cp.minigames.minicactpot.domain.model.util.AggregateConstants.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@RequiredArgsConstructor
public abstract class AbstractReactiveMongoRepository<T extends Aggregate> implements ReactiveRepository<T> {

    protected final Class<T> clazz;
    protected final String collectionName;
    protected final ReactiveMongoTemplate template;

    @Override
    public Mono<T> findById(String id) {
        return template.findById(id, clazz, collectionName);
    }

    @Override
    public Flux<T> query(MultiValueMap<String, String> queryMap, long page, long limit) {
        Query query = buildPaginatedQuery(queryMap, page, limit);

        return template.query(clazz)
            .inCollection(collectionName)
            .matching(query)
            .all();
    }

    @Override
    public Mono<Long> count(MultiValueMap<String, String> queryMap) {
        Query query = buildQuery(queryMap);

        return template.count(query, clazz, collectionName);
    }

    @Override
    public Mono<Tuple2<List<T>, Long>> queryWithCount(MultiValueMap<String, String> queryMap, long page, long limit) {
        Query query = buildPaginatedQuery(queryMap, page, limit);
        Query countQuery = Query.of(query).limit(0).skip(0);

        return Mono.zip(
            template.query(clazz).inCollection(collectionName).matching(query).all().collectList(),
            template.count(countQuery, clazz, collectionName)
        );
    }

    @Override
    public Mono<T> insert(T aggregate) {
        return template.insert(clazz)
            .inCollection(collectionName)
            .one(aggregate);
    }

    @Override
    public Mono<T> update(T aggregate) {
        return template.update(clazz)
            .inCollection(collectionName)
            .matching(Query.query(where(ID).is(aggregate.getId())))
            .replaceWith(aggregate)
            .withOptions(FindAndReplaceOptions.empty().returnNew())
            .findAndReplace();
    }

    @Override
    public Mono<Long> deleteById(String id) {
        return template.remove(clazz)
            .inCollection(collectionName)
            .matching(Query.query(where(ID).is(id)))
            .all()
            .map(DeleteResult::getDeletedCount);
    }

    // --

    protected Query buildQuery(MultiValueMap<String, String> queryMap) {
        return MongoQueryBuilder.init()
            .withDateFrom(CREATED_DATE, queryMap.getFirst(CREATED_DATE_FROM))
            .withDateTo(CREATED_DATE, queryMap.getFirst(CREATED_DATE_TO))
            .withDateFrom(UPDATED_DATE, queryMap.getFirst(UPDATED_DATE_FROM))
            .withDateTo(UPDATED_DATE, queryMap.getFirst(UPDATED_DATE_TO))
            .build();
    }

    protected Query buildPaginatedQuery(MultiValueMap<String, String> queryMap, long page, long limit) {
        Query query = buildQuery(queryMap);

        if (limit == 0) return query;

        query.limit(Long.valueOf(limit).intValue());
        query.skip((page - 1) * limit);

        return query;
    }
}
