package com.mitocode.repository;

import com.mitocode.model.User;
import reactor.core.publisher.Mono;

public interface UserRepository extends GenericRepository<User, String>{

    Mono<User> findOneByUsername(String username);
}
