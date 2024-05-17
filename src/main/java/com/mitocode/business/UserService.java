package com.mitocode.business;

import com.mitocode.model.User;
import reactor.core.publisher.Mono;

public interface UserService extends CrudService<User, String> {
  Mono<User> saveHash(User user);
  Mono<com.mitocode.security.User> searchByUser(String username);

}
