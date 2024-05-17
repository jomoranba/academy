package com.mitocode.business.impl;

import com.mitocode.business.UserService;
import com.mitocode.model.Role;
import com.mitocode.model.User;
import com.mitocode.repository.GenericRepository;
import com.mitocode.repository.RoleRepository;
import com.mitocode.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends CrudServiceImpl<User, String> implements UserService {

  private final UserRepository userRepo;
  private final RoleRepository roleRepo;
  private final BCryptPasswordEncoder bcrypt;

  @Override
  protected GenericRepository<User, String> getRepo() {
    return userRepo;
  }

  @Override
  public Mono<User> saveHash(User user) {
    user.setPassword(bcrypt.encode(user.getPassword()));
    return userRepo.save(user);
  }

  @Override
  public Mono<com.mitocode.security.User> searchByUser(String username) {
    return userRepo.findOneByUsername(username)
      .flatMap(user -> Flux.fromIterable(user.getRoles())
        .flatMap(userRole -> roleRepo.findById(userRole.getId())
          .map(Role::getName))
        .collectList()
        .map(roles -> new com.mitocode.security.User(user.getUsername(), user.getPassword(), user.isStatus(), roles))
      );
  }
}
