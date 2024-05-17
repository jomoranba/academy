package com.mitocode.business;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CrudService<T, ID>{

  Mono<T> findById(ID id);

  Flux<T> findAll();

  Mono<T> save(T t);

  Mono<T> update(ID id, T t);

  Mono<Boolean> deleteById(ID id);
}
