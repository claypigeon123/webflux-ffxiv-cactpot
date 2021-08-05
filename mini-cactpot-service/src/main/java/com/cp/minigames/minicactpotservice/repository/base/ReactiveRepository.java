package com.cp.minigames.minicactpotservice.repository.base;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface ReactiveRepository<T, ID> {
    Flux<T> query(Map<String, String> queryParams);

    Mono<T> findById(ID id);

    Mono<T> upsert(T aggregate);

    Mono<Void> delete(ID id);
}
