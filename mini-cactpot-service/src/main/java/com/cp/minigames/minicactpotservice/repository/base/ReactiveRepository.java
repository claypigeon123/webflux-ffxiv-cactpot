package com.cp.minigames.minicactpotservice.repository.base;

import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface ReactiveRepository<T, ID> {
    Flux<T> query(MultiValueMap<String, String> queryParams);

    Mono<T> findById(ID id);

    Mono<T> upsert(T aggregate);

    Mono<Void> delete(ID id);
}
