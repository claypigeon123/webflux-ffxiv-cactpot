package com.cp.minigames.minicactpotservice.repository;

import com.cp.minigames.minicactpot.domain.model.aggregate.Aggregate;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactiveRepository<T extends Aggregate> {
    Mono<T> findById(String id);

    Mono<Boolean> existsById(String id);

    Flux<T> findAll();

    Flux<T> query(MultiValueMap<String, String> queryMap, long page, long limit);

    Mono<Long> count(MultiValueMap<String, String> queryMap);

    Mono<T> insert(T item);

    Mono<T> update(T item);

    Mono<Long> deleteById(String id);
}
