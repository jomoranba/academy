package com.mitocode.business.impl;

import com.mitocode.business.CrudService;
import com.mitocode.repository.GenericRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class CrudServiceImpl<T, ID> implements CrudService<T, ID> {

  protected abstract GenericRepository<T, ID> getRepo();

  public Mono<T> findById(ID id) {
    return getRepo().findById(id);
  }

  public Flux<T> findAll() {
    return getRepo().findAll();
  }

  public Mono<T> save(T t) {
    return getRepo().save(t);
  }

  public Mono<T> update(ID id, T t) {
    return getRepo().findById(id).flatMap(e -> getRepo().save(t));
  }

  public Mono<Boolean> deleteById(ID id) {
    return getRepo().existsById(id)
      .flatMap(result -> {
        if (Boolean.TRUE.equals(result)) {
          return getRepo().deleteById(id).then(Mono.just(true));
        } else {
          return Mono.just(Boolean.FALSE);
        }
      });
  }
}
