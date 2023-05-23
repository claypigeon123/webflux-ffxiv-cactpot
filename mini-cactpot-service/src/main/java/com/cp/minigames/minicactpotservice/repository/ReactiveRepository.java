package com.cp.minigames.minicactpotservice.repository;

import com.cp.minigames.minicactpot.domain.model.aggregate.Aggregate;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.List;

public interface ReactiveRepository<T extends Aggregate> {
    Mono<T> findById(String id);

    Flux<T> query(MultiValueMap<String, String> queryMap, long page, long limit);

    Mono<Long> count(MultiValueMap<String, String> queryMap);

    Mono<Tuple2<List<T>, Long>> queryWithCount(MultiValueMap<String, String> queryMap, long page, long limit);

    Mono<T> insert(T item);

    Mono<T> update(T item);

    Mono<Long> deleteById(String id);
}
